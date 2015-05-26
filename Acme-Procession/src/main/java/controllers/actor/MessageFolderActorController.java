package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.MessageFolderService;
import controllers.AbstractController;
import domain.MessageFolder;

@Controller
@RequestMapping("/messageFolder/actor")
public class MessageFolderActorController extends AbstractController {
	// Services ---------------------------------------------------------------
	
	@Autowired
	private MessageFolderService messageFolderService;
	
	// Constructors -----------------------------------------------------------
	
	public MessageFolderActorController() {
		super();
	}
	
	// Listing ----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<MessageFolder> messageFolders;
		
		messageFolders = messageFolderService.findPrincipalFolders();
		
		result = new ModelAndView("messageFolder/list");
		result.addObject("requestURI", "messageFolder/actor/list.do");
		result.addObject("messageFolders", messageFolders);
		
		return result;
	}
	
	// Creation ---------------------------------------------------------------
	
	// Edition ----------------------------------------------------------------
	
	// Ancillary methods ------------------------------------------------------
	
}
