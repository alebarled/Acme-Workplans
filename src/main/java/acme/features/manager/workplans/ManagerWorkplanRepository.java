package acme.features.manager.workplans;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.roles.Manager;
import acme.entities.workplans.Workplan;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface ManagerWorkplanRepository extends AbstractRepository{
	
	
	@Query("select w from Workplan w where w.manager.id = :managerId")
	Collection<Workplan> findManyByManagerId(int managerId);
	
	@Query("select w from Workplan w where w.id = ?1")
	Workplan findOneWorkplanById(int id);
	
	@Query("select m from Manager m where m.id = :managerId")
	Manager findManagerById(int managerId);


}
