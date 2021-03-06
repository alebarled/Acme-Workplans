package acme.features.manager.workplans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.entities.workplans.Workplan;
import acme.features.manager.tasks.ManagerTaskRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractUpdateService;
import acme.validators.spamValidator.SpamValidatorService;

@Service
public class ManagerWorkplanUpdateService implements AbstractUpdateService<Manager, Workplan>{
	
	@Autowired
	ManagerWorkplanRepository repository;
	
	@Autowired
	ManagerTaskRepository taskRepository;
	
	@Autowired
	protected SpamValidatorService spamValidatorService;

	@Override
	public boolean authorise(final Request<Workplan> request) {
		assert request != null;
		
		final Workplan workplan = this.repository.findOneWorkplanById(request.getModel().getInteger("id"));
		if(workplan.getManager().getId() != request.getPrincipal().getActiveRoleId())
			return false;
		
		if(request.getMethod().toString().equals("POST")) {
			final Errors errors = new Errors();
			final Workplan wl = new Workplan();
			request.bind(wl, errors);
			
			Set<String> newTasks = wl.getNewTasks();
			if(newTasks==null)
				newTasks = new HashSet<>();
			
			final List<String> managerTasksIds = this.taskRepository.findManyByManagerId(request.getPrincipal().getActiveRoleId())
			.stream()
			.map(x->String.valueOf(x.getId()))
			.collect(Collectors.toList());
	
			return managerTasksIds.containsAll(newTasks);
		}
		else
			return true;
	}

	@Override
	public void bind(final Request<Workplan> request, final Workplan entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		
		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Workplan> request, final Workplan entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		
		final Collection<Task> managerTasks = this.taskRepository.findManyByManagerId(request.getPrincipal().getActiveRoleId());
		model.setAttribute("managerTasks", managerTasks);
		model.setAttribute("language", LocaleContextHolder.getLocale());
		request.unbind(entity, model, "title", "description", "executionStart", "executionEnd", "isPublic", "tasks");
		
	}

	@Override
	public Workplan findOne(final Request<Workplan> request) {
		return this.repository.findOneWorkplanById(request.getModel().getInteger("id"));
	}

	@Override
	public void validate(final Request<Workplan> request, final Workplan entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		if(entity.getNewTasks()!=null) {
			entity.setTasks(entity.getNewTasks().stream()
				.map(x->this.taskRepository.findOneTaskById(Integer.parseInt(x)))
				.collect(Collectors.toList()));
		}else {
			entity.setTasks(new ArrayList<>());
		}
		
		/// Private tasks validate
		
		if(entity.getIsPublic().equals(true)) {
			final Long privateTasks = entity.getTasks().stream().filter(x->!x.getIsPublic()).count();
			errors.state(request, privateTasks.equals(0L), "isPublic", "acme.validators.privatetasks");
		}
		
		/// Dates validate
		
		if(!errors.hasErrors("executionStart") && !errors.hasErrors("executionEnd"))
			errors.state(request, entity.getExecutionStart().before(entity.getExecutionEnd()), "executionEnd", "acme.validators.validdates");
		
		if(entity.getNewTasks()!=null && !errors.hasErrors("executionStart") && !errors.hasErrors("executionEnd")) {
			final Date maxEnd = entity.getTasks().stream().map(Task::getExecutionEnd).max(Comparator.naturalOrder()).orElse(null);
			final Date minStart = entity.getTasks().stream().map(Task::getExecutionStart).min(Comparator.naturalOrder()).orElse(null);
			
			final Boolean correct = entity.getExecutionStart().compareTo(minStart)<=0 &&
				entity.getExecutionEnd().compareTo(maxEnd)>=0;
			
			errors.state(request, correct, "executionEnd", "acme.validators.workplanperiod");
			
		}
		/// Spam validate
		
			if (!errors.hasErrors("description")) 
				errors.state(request, this.spamValidatorService.spamValidate(entity.getDescription()), "description", "acme.validators.spamtext");
			
			if (!errors.hasErrors("title"))
				errors.state(request, this.spamValidatorService.spamValidate(entity.getTitle()), "title", "acme.validators.spamtext");
			
		
		if(errors.hasErrors()) {
            this.unbind(request,entity,request.getModel());
		}
		
	}

	@Override
	public void update(final Request<Workplan> request, final Workplan entity) {
		assert request != null;
		assert entity != null;
		
		if(entity.getNewTasks()!=null) {
			entity.setTasks(entity.getNewTasks().stream()
				.map(x->this.taskRepository.findOneTaskById(Integer.parseInt(x)))
				.collect(Collectors.toList()));
		}else {
			entity.setTasks(new ArrayList<>());
		}
		
		this.repository.save(entity);
		
	}

}
