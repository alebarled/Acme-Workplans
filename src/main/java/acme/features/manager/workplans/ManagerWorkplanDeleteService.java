package acme.features.manager.workplans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.workplans.Workplan;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractDeleteService;

@Service
public class ManagerWorkplanDeleteService implements AbstractDeleteService<Manager, Workplan>{
	
	@Autowired
	ManagerWorkplanRepository repository;

	@Override
	public boolean authorise(final Request<Workplan> request) {
		assert request !=null;
		return true;
	}

	@Override
	public void bind(final Request<Workplan> request, final Workplan entity, final Errors errors) {
		//NotUsed
		
	}

	@Override
	public void unbind(final Request<Workplan> request, final Workplan entity, final Model model) {
		//NotUsed
		
	}

	@Override
	public Workplan findOne(final Request<Workplan> request) {
		
		Workplan result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneWorkplanById(id);

		return result;
	}

	@Override
	public void validate(final Request<Workplan> request, final Workplan entity, final Errors errors) {
		//NotUsed
		
	}

	@Override
	public void delete(final Request<Workplan> request, final Workplan entity) {
		assert request != null;
		assert entity != null;	
				
		this.repository.delete(entity);
		
	}

}
