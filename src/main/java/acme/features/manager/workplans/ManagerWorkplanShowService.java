package acme.features.manager.workplans;

import acme.entities.roles.Manager;
import acme.entities.workplans.Workplan;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

public class ManagerWorkplanShowService implements AbstractShowService<Manager, Workplan>{

	@Override
	public boolean authorise(Request<Workplan> request) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unbind(Request<Workplan> request, Workplan entity, Model model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Workplan findOne(Request<Workplan> request) {
		// TODO Auto-generated method stub
		return null;
	}

}
