package acme.entities.workplans;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Workplan extends DomainEntity{
	
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	
	@Temporal(TemporalType.TIMESTAMP)
	Date executionStart;
	
	@Temporal(TemporalType.TIMESTAMP)
	Date executionEnd;
	
	@NotNull
	Boolean isPublic;
	
	// Relationships ----------------------------------------------------------
	
	@Valid
	@ManyToMany(cascade = CascadeType.REFRESH)
	List<Task> tasks;
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Manager manager;
	
	// Derived attributes -----------------------------------------------------
	
	public Double getWorkload(){
		return this.tasks.stream().mapToDouble(x->(double)x.getWorkload()).sum();
	}

}
