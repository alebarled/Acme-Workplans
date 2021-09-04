package acme.features.manager.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractUpdateService;
import acme.validators.spamValidator.SpamValidatorService;

@Service
public class ManagerTaskUpdateService implements AbstractUpdateService<Manager, Task> {
	
	@Autowired
	protected ManagerTaskRepository repository;
	
	@Autowired
	protected SpamValidatorService spamValidatorService;

	@Override
	public boolean authorise(final Request<Task> request) {
		assert request != null;
		final Task task = this.repository.findOneTaskById(request.getModel().getInteger("id"));
		return task.getManager().getId() == request.getPrincipal().getActiveRoleId();
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
	public Task findOne(final Request<Task> request) {
		assert request != null;
		
		Task result;
		int id;
		
		id = request.getModel().getInteger("id");
		result = this.repository.findOneTaskById(id);
		
		return result;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void validate(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		/// tiene errores workload?
		boolean resworkload = !errors.hasErrors("workload");
		
		if(resworkload) {
			// actualizamos si tiene errores workload
			resworkload = entity.getWorkload() >= 0 && entity.getWorkload() <= 99.59;
			errors.state(request, resworkload, "workload", "acme.validators.validworkload");
		}
		
		if(resworkload) {
			final float workLoadDecimals = entity.getWorkload() - entity.getWorkload().intValue();
			resworkload = workLoadDecimals < 0.595;
			errors.state(request, resworkload, "workload", "acme.validators.validworkloaddecimals");
		}
		
		if(resworkload) {
			final String strWorkload = String.valueOf(entity.getWorkload());
			if (strWorkload.contains(".")) {		
				resworkload = strWorkload.split("\\.")[1].length()<=2;
				errors.state(request, strWorkload.split("\\.")[1].length()<=2, "workload", "acme.validators.validworkloadtoomuchdecimals");
			}
			
		}
		
		
		
		if (!errors.hasErrors("executionStart") && !errors.hasErrors("executionEnd")) {
			
			
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
	public void update(final Request<Task> request, final Task entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
	}
	
	


}
