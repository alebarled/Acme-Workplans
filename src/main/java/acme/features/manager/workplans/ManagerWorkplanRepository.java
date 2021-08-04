package acme.features.manager.workplans;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.workplans.Workplan;
import acme.framework.repositories.AbstractRepository;

public interface ManagerWorkplanRepository extends AbstractRepository{
	
	
	@Query("select w from Workplan w where w.manager.id = :managerId")
	Collection<Workplan> findManyByManagerId(int managerId);
	
	@Query("select w from Workplan w where w.id = ?1")
	Workplan findOneWorkplanById(int id);

}
