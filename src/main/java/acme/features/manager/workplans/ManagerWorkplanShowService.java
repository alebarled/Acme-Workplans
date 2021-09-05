package acme.features.manager.workplans;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.entities.workplans.Workplan;
import acme.features.manager.tasks.ManagerTaskRepository;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

@Service
public class ManagerWorkplanShowService implements AbstractShowService<Manager, Workplan>{
	
	//Internal state --------------------------------------------------
	@Autowired
	ManagerWorkplanRepository repository;
	
	@Autowired
	ManagerTaskRepository taskRepository;

	@Override
	public boolean authorise(final Request<Workplan> request) {
		assert request !=null;
		final Workplan workplan = this.repository.findOneWorkplanById(request.getModel().getInteger("id"));
		if(workplan==null)
			return false;
		else
			return workplan.getManager().getId() == request.getPrincipal().getActiveRoleId();
	}

	@Override
	public void unbind(final Request<Workplan> request, final Workplan entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		final Collection<Task> managerTasks = this.taskRepository.findManyByManagerId(request.getPrincipal().getActiveRoleId());
		
		model.setAttribute("managerTasks", managerTasks);
		model.setAttribute("language", LocaleContextHolder.getLocale());
		request.unbind(entity, model, "id","title","description","executionStart", "executionEnd","isPublic","tasks","manager","workload");
		
	}

	@Override
	public Workplan findOne(final Request<Workplan> request) {

		return this.repository.findOneWorkplanById(request.getModel().getInteger("id"));
	}

}
