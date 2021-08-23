package acme.forms;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dashboard implements Serializable {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	

	String numberOfPublicTasks;
	

	String numberOfPrivateTasks;
	
	
	

	String numberOfFinishedTasks;
	

	String numberOfNonFinishedTasks;
	
	
	

	String avarageWorkloads;
	

	String minimumWorkloads;
	

	String maximumWorkloads;
	

	String deviationWorkload;
	
	
	

	String avarageExecPeriod;
	

	String minimumExecPeriod;
	

	String maximumExecPeriod;
	

	String deviationExecPeriod;
	
	
	//------------- WORKPLAN DATA ------------------------------------------------------------
	
	
	String numberOfPublicWorkplans;
	

	String numberOfPrivateWorkplans;
	

	String numberOfFinishedWorkplans;
	

	String numberOfNonFinishedWorkplans;
	
	
	String avarageWorkloadsWorkplan;
	

	String minimumWorkloadsWorkplan;
	

	String maximumWorkloadsWorkplan;
	

	String deviationWorkloadWorkplan;
	

	String avarageExecPeriodWorkplan;
	

	String minimumExecPeriodWorkplan;
	

	String maximumExecPeriodWorkplan;
	

	String deviationExecPeriodWorkplan;
	

}
	
	

