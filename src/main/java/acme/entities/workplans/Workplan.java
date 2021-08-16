package acme.entities.workplans;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

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
	
	@NotBlank
	@Length(min=1, max=80)
	protected String title;
	
	@NotBlank
	@Column(columnDefinition="varchar(500)")
	@Length(min = 1, max = 500)
	protected String description;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	Date executionStart;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	Date executionEnd;
	
	@NotNull
	Boolean isPublic;
	
	@Transient
    private List<String> newTasks;
	
	// Relationships ----------------------------------------------------------
	
	@Valid
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	List<Task> tasks;
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Manager manager;
	
	// Derived attributes -----------------------------------------------------
	
	public Double getWorkload(){
		Integer hours = 0;
		Integer minutes = 0;
		
		for(final Task t: this.tasks) {
			final String[] array = t.getWorkload().toString().split("\\.");
			String minuteSt = array[1];
			while(minuteSt.length()<2)
				minuteSt = minuteSt+"0";
			hours += Integer.parseInt(array[0]);
			minutes += Integer.parseInt(minuteSt);
		}
		hours += minutes / 60;
		minutes = minutes % 60;
		
		String stringMinutes = String.valueOf(minutes);
		
		while(stringMinutes.length()<2)
			stringMinutes = "0"+stringMinutes;
		
		
		return Double.parseDouble(hours+"."+stringMinutes);
	}
	

}
