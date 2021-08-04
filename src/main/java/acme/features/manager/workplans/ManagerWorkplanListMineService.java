package acme.features.manager.workplans;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.roles.Manager;
import acme.entities.workplans.Workplan;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractListService;

public class ManagerWorkplanListMineService implements AbstractListService<Manager, Workplan>{
	
	//Internal state --------------------------------------------------
	@Autowired
	ManagerWorkplanRepository repository;

	@Override
	public boolean authorise(final Request<Workplan> request) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unbind(final Request<Workplan> request, final Workplan entity, final Model model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Workplan> findMany(final Request<Workplan> request) {
		assert request!=null;
		 return this.repository.findManyByManagerId(request.getPrincipal().getActiveRoleId());
	}

}
