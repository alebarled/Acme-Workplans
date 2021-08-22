package acme.features.anonymous.workplans;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.workplans.Workplan;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnonymousWorkplanRepository extends AbstractRepository{
	
	@Query("select w from Workplan w where w.executionEnd>=current_timestamp and w.isPublic = 1")
	Collection<Workplan> findMany();
	
	@Query("select w from Workplan w where w.id = ?1")
	Workplan findOneWorkplanById(int id);
	
	@Query("select w from Workplan w where w.executionEnd>=current_timestamp and w.isPublic = 1 and w.id = ?1")
	Workplan findValidWorkplanById(int id);
}
