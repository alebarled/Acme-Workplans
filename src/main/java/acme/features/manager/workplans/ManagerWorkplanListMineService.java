package acme.features.manager.workplans;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.workplans.Workplan;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractListService;

@Service
public class ManagerWorkplanListMineService implements AbstractListService<Manager, Workplan>{
	
	//Internal state --------------------------------------------------
	@Autowired
	ManagerWorkplanRepository repository;

	@Override
	public boolean authorise(final Request<Workplan> request) {
		assert request !=null;
		return true;
	}

	@Override
	public void unbind(final Request<Workplan> request, final Workplan entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "id","executionStart", "executionEnd","isPublic","workload");
		
	}

	@Override
	public Collection<Workplan> findMany(final Request<Workplan> request) {
		assert request!=null;
		 return this.repository.findManyByManagerId(request.getPrincipal().getActiveRoleId());
	}

}
