package acme.features.manager.tasks;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractListService;


@Service
public class ManagerTaskListMineService implements AbstractListService<Manager, Task> {

	//Internal state --------------------------------------------------
	@Autowired
	ManagerTaskRepository repository;
	
	
	@Override
	public boolean authorise(final Request<Task> request) {
		assert request !=null;
		return true;
	}

	@Override
	public void unbind(final Request<Task> request, final Task entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "title", "executionStart", "executionEnd", "workload", "description", "isPublic", "link");
		
	}

	@Override
	public Collection<Task> findMany(final Request<Task> request) {
		assert request!=null;
		Collection<Task> result;
		result = this.repository.findManyByManagerId(request.getPrincipal().getActiveRoleId());
		result = result.stream().sorted(
			(x2,x1)-> {
				if(x1.getWorkload().compareTo(x2.getWorkload())==0) 
					return x1.getPeriod().compareTo(x2.getPeriod());
				else
					return x1.getWorkload().compareTo(x2.getWorkload());
			}
			).collect(Collectors.toList());
		return result;
	}

}
