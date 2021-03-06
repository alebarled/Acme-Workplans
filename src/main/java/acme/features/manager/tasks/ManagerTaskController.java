package acme.features.manager.tasks;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/manager/task")
public class ManagerTaskController extends AbstractController<Manager, Task> {
	
	//Internal state ---------------------------------------------
	@Autowired
	private ManagerTaskListMineService listMineService;
	
	@Autowired
	private ManagerTaskShowService showService;
	
	@Autowired
	private ManagerTaskCreateService createService;
	
	@Autowired
	private ManagerTaskUpdateService updateService;
	
	@Autowired
	private ManagerTaskDeleteService deleteService;
	
	//Constructors ------------------------------------------------
	@PostConstruct
	private void initialise() {
		super.addCustomCommand(CustomCommand.LIST_MINE, BasicCommand.LIST, this.listMineService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
		super.addBasicCommand(BasicCommand.DELETE, this.deleteService);		
		super.addBasicCommand(BasicCommand.UPDATE, this.updateService);
	}

}
