package acme.features.anonymous.workplans;

import org.springframework.stereotype.Service;

import acme.entities.workplans.Workplan;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractShowService;

@Service
public class AnonymousWorkplanShowService implements AbstractShowService<Anonymous, Workplan> {

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
	public Workplan findOne(final Request<Workplan> request) {
		// TODO Auto-generated method stub
		return null;
	}



}
