package acme.features.anonymous.workplans;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.workplans.Workplan;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractListService;

@Service
public class AnonymousWorkplanListService implements AbstractListService<Anonymous, Workplan> {
	
	@Autowired
	AnonymousWorkplanRepository repository;

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
		
		request.unbind(entity, model, "title","executionStart", "executionEnd","isPublic","workload");
		
	}

	@Override
	public Collection<Workplan> findMany(final Request<Workplan> request) {
		return this.repository.findMany().stream().sorted(
			(x1,x2)-> {
				if(x1.getPeriod()<x2.getPeriod()) {
					return 1;
				}else if(x1.getPeriod()>x2.getPeriod()) {
					return -1;
				}else {
					if(x1.getWorkload()<x2.getWorkload()) {
						return 1;
					}else if(x1.getWorkload()>x2.getWorkload()) {
						return -1;
					}else {
						return 0;
					}
				}
			}
			).collect(Collectors.toList());
	}

}
