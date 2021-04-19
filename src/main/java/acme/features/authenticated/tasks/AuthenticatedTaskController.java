package acme.features.authenticated.tasks;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.tasks.Task;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Authenticated;

@Controller
@RequestMapping("/authenticated/task")
public class AuthenticatedTaskController extends AbstractController<Authenticated, Task> {
	
	//Internal state ----------------------------------------------------
	@Autowired
	private AuthenticatedTaskListService listService;
	
	//Contructors -------------------------------------------------------
	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
	}
	

	
}