package acme.features.manager.workplans;

import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.workplans.Workplan;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractUpdateService;

@Service
public class ManagerWorkplanUpdateService implements AbstractUpdateService<Manager, Workplan>{

	@Override
	public boolean authorise(final Request<Workplan> request) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void bind(final Request<Workplan> request, final Workplan entity, final Errors errors) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unbind(final Request<Workplan> request, final Workplan entity, final Model model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Workplan findOne(final Request<Workplan> request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validate(final Request<Workplan> request, final Workplan entity, final Errors errors) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(final Request<Workplan> request, final Workplan entity) {
		// TODO Auto-generated method stub
		
	}

}
