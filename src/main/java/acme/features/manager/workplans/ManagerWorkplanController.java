package acme.features.manager.workplans;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.roles.Manager;
import acme.entities.workplans.Workplan;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/manager/workplan")
public class ManagerWorkplanController extends AbstractController<Manager, Workplan> {
	
	//Internal state ---------------------------------------------
	@Autowired
	private ManagerWorkplanListMineService listMineService;
	
	@Autowired
	private ManagerWorkplanShowService showService;
	
	@Autowired
	private ManagerWorkplanCreateService createService;
	
	@Autowired
	private ManagerWorkplanUpdateService updateService;
	
	@Autowired
	private ManagerWorkplanDeleteService deleteService;
	
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