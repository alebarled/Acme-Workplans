package acme.features.anonymous.workplans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.workplans.Workplan;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractShowService;

@Service
public class AnonymousWorkplanShowService implements AbstractShowService<Anonymous, Workplan> {
	
	@Autowired
	AnonymousWorkplanRepository repository;

	@Override
	public boolean authorise(final Request<Workplan> request) {
		assert request !=null;
		final Workplan workplan = this.repository.findValidWorkplanById(request.getModel().getInteger("id"));
		
		if(workplan!=null)
			return true;
		else
			return false;
	}

	@Override
	public void unbind(final Request<Workplan> request, final Workplan entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "id","title","description","executionStart", "executionEnd","isPublic","tasks","manager","workload");
		
	}

	@Override
	public Workplan findOne(final Request<Workplan> request) {
		return this.repository.findOneWorkplanById(request.getModel().getInteger("id"));
	}



}
