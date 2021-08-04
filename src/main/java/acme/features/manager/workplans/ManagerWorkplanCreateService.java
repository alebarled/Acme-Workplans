package acme.features.manager.workplans;

import acme.entities.roles.Manager;
import acme.entities.workplans.Workplan;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

public class ManagerWorkplanCreateService implements AbstractCreateService<Manager, Workplan>{

	@Override
	public boolean authorise(Request<Workplan> request) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void bind(Request<Workplan> request, Workplan entity, Errors errors) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unbind(Request<Workplan> request, Workplan entity, Model model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Workplan instantiate(Request<Workplan> request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validate(Request<Workplan> request, Workplan entity, Errors errors) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(Request<Workplan> request, Workplan entity) {
		// TODO Auto-generated method stub
		
	}

}
