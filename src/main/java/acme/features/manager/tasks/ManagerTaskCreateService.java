package acme.features.manager.tasks;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.spamValidator.SpamValidatorService;
import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class ManagerTaskCreateService implements AbstractCreateService<Manager, Task> {
	
	@Autowired
	protected ManagerTaskRepository repository;
	
	@Autowired
	protected SpamValidatorService spamValidatorService;

	@Override
	public boolean authorise(final Request<Task> request) {
		assert request != null;
		
		return true;
	}

	@Override
	public void bind(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		
		request.bind(entity, errors);
		
	}

	@Override
	public void unbind(final Request<Task> request, final Task entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		
		request.unbind(entity, model, "title", "executionStart", "executionEnd", "workload", "description", "isPublic", "link");
		
	}

	@Override
	public Task instantiate(final Request<Task> request) {
		
		assert request != null;
		final Task t = new Task();
		t.setManager(this.repository.findManagerById(request.getPrincipal().getActiveRoleId()));
		return t;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void validate(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		/// tiene errores workload?
		boolean resworkload = !errors.hasErrors("workload");
		
		if(!errors.hasErrors("workload")) {
			final float workLoadDecimals = entity.getWorkload() - entity.getWorkload().intValue();
			
			// actualizamos si tiene errores workload
			resworkload = entity.getWorkload() >= 0 && workLoadDecimals <= 0.59;
			errors.state(request, resworkload, "workload", "acme.validators.validworkloaddecimals");
		}
		
		if(!errors.hasErrors("executionStart")) {
			final boolean futureStart = entity.getExecutionStart().after(new Date());
			errors.state(request, futureStart, "executionStart", "javax.validation.constraints.Future.message");
		}
		
		if(!errors.hasErrors("executionEnd")) {
			final boolean futureEnd = entity.getExecutionEnd().after(new Date());
			errors.state(request, futureEnd, "executionEnd", "javax.validation.constraints.Future.message");
		}
		
		
		if (!errors.hasErrors("executionStart") && !errors.hasErrors("executionEnd")) {
			
			final Date now = new Date();
			
			now.setHours(LocalDateTime.now().getHour());
			now.setMinutes(LocalDateTime.now().getMinute());
			
			final boolean futureStart = entity.getExecutionStart().after(now);
			final boolean futureEnd = entity.getExecutionEnd().after(now);
			
			if(futureStart && futureEnd)
				errors.state(request, entity.getExecutionStart().before(entity.getExecutionEnd()), "executionEnd", "acme.validators.validdates");
			
			// si no dio el error de los decimales evaluamos con el periodo
			if(resworkload) {
				final boolean res =  entity.getPeriod() >= entity.getWorkload();
				errors.state(request, res, "workload", "acme.validators.validworkloadperiod");
			}
		}
			
		
		
		///spam validate
	
		
		if (!errors.hasErrors("description")) 
				errors.state(request, this.spamValidatorService.spamValidate(entity.getDescription()), "description", "acme.validators.spamtext");
		
		if (!errors.hasErrors("title"))
			errors.state(request, this.spamValidatorService.spamValidate(entity.getTitle()), "title", "acme.validators.spamtext");
		
		if (!errors.hasErrors("link"))
			errors.state(request, this.spamValidatorService.spamValidate(entity.getLink()), "link", "acme.validators.spamtext");
		
		
		}
		
		

	@Override
	public void create(final Request<Task> request, final Task entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
		
	}

}
