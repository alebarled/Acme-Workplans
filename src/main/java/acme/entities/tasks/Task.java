package acme.entities.tasks;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.roles.Manager;
import acme.entities.workplans.Workplan;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = { @Index(columnList= "isPublic, executionEnd, workload, id, manager_id")})
public class Task extends DomainEntity{
	
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	
	@NotBlank
	@Length(min=1, max=80)
	protected String title;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date executionStart;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date executionEnd;
	
	@NotNull
	protected Float workload;
	
	@NotBlank
	@Column(columnDefinition="varchar(500)")
	@Length(min = 1, max = 500)
	protected String description;
	
	@NotNull
	protected Boolean isPublic;
	
	@URL
	protected String link;
	
	// Derived attributes -----------------------------------------------------

	public Float getPeriod() {
		final Double diff = (double)(this.getExecutionEnd().getTime() - this.getExecutionStart().getTime());
		
		final Float hours = (float) (diff/ (1000 * 60 * 60));
		final Float minsDec = (float) (((diff / (1000 * 60)) % 60)/100);
		return hours.intValue()+minsDec;
	}
	

	// Relationships ----------------------------------------------------------


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Manager manager;
	
	@Valid
    @ManyToMany(mappedBy = "tasks", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private List<Workplan> workplans;
	
	public Boolean getFinished() {
		return this.executionEnd.before(Date.from(Instant.now())) || 
			this.executionEnd.equals(Date.from(Instant.now()));
	}

}
	
	

