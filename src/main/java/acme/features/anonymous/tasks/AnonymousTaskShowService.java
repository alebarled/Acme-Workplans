package acme.features.anonymous.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tasks.Task;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractShowService;

@Service
public class AnonymousTaskShowService implements AbstractShowService<Anonymous, Task>{

	//Internal state -----------------------------------------------------
	@Autowired
	AnonymousTaskRepository repository;
	
	@Override
	public boolean authorise(final Request<Task> request) {
		assert request != null;
		final Task task = this.repository.findOneTaskById(request.getModel().getInteger("id"));
		
		return this.repository.findMany().contains(task);
	}

	@Override
	public void unbind(final Request<Task> request, final Task entity, final Model model) {
		assert request !=null;
		assert entity !=null;
		assert model !=null;
		
		request.unbind(entity, model, "title", "executionStart", "executionEnd", "workload", "description", "isPublic", "link");
		
	}

	@Override
	public Task findOne(final Request<Task> request) {
		assert request != null;
		final int id = request.getModel().getInteger("id");
		final Task result = this.repository.findOneTaskById(id);
		return result;
	}

}
