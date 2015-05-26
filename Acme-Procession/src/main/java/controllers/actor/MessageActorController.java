package controllers.actor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.MessageFolderService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Message;
import domain.MessageFolder;

@Controller
@RequestMapping("/message/actor")
public class MessageActorController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private MessageFolderService messageFolderService;
	
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------------------------
	
	public MessageActorController() {
		super();
	}
	
	// Listing ----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int messageFolderId) {
		ModelAndView result;
		MessageFolder messageFolder;
		Collection<Message> messages;
		
		messageFolder = messageFolderService.findOneIfPrincipal(messageFolderId);
		messages = messageService.findByMessageFolder(messageFolder);
		
		result = new ModelAndView("message/list");
		result.addObject("requestURI", "message/actor/list.do");
		result.addObject("messages", messages);
		
		return result;
	}
	
	
	// Creation ---------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Message message;
		
		message = messageService.create();
		
		result = createEditModelAndView(message);
		
		return result;
	}
	
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public ModelAndView createReply(@RequestParam int id) {
		ModelAndView result;
		Message message;
		
		message = messageService.createReply(messageService.findOne(id));
		
		result = replyModelAndView(message);

		return result;
	}
	
	// Edition ----------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "send")
	public ModelAndView save(@ModelAttribute(value="sendMessage") @Valid Message message, BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(message);
		} else {
			try {
				messageService.sendMessage(message);
				result = new ModelAndView("redirect:/messageFolder/actor/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(message, "message.commit.error");
			}
		}
		
		return result;
	}
	
	@RequestMapping(value = "/reply", method = RequestMethod.POST, params = "sendReply")
	public ModelAndView saveReply(@ModelAttribute(value="sendMessage") @Valid Message message, BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()) {
			result = replyModelAndView(message);
		} else {
			try {
				messageService.sendMessage(message);
				result = new ModelAndView("redirect:/messageFolder/actor/list.do");
			} catch (Throwable oops) {
				result = replyModelAndView(message, "message.commit.error");
			}
		}
		
		return result;
	}
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int messageId) {
		ModelAndView result;
		Message message;
		boolean canReply;
		
		message = messageService.findOneIfOwner(messageId);
		canReply = messageService.isRecipient(message, actorService.findByPrincipal());
		
		result = new ModelAndView("message/details");
		result.addObject("msg", message);
		result.addObject("canReply", canReply);
		
		return result;
	}
	
	@RequestMapping(value = "/details", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@ModelAttribute(value="msg") @Valid Message message, BindingResult binding) {
		ModelAndView result;
		
		try {			
			messageService.deleteOrMoveToTrashbox(message);
			result = new ModelAndView("redirect:../../messageFolder/actor/list.do");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:../../messageFolder/actor/list.do");
		}
		
		return result;
	}
		
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Message message) {
		ModelAndView result;

		result = createEditModelAndView(message, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(Message msg, String text) {
		ModelAndView result;
		Collection<Actor> recipients;
		
		recipients = actorService.findAllExceptMe();
		
		result = new ModelAndView("message/create");
		result.addObject("formAction", "message/actor/create.do");
		result.addObject("sendMessage", msg);
		result.addObject("recipients", recipients);
		result.addObject("message", text);
		result.addObject("isReply", false);
		
		return result;
	}
	
	protected ModelAndView replyModelAndView(Message message) {
		ModelAndView result;

		result = replyModelAndView(message, null);
		
		return result;
	}	
	
	protected ModelAndView replyModelAndView(Message msg, String text) {
		ModelAndView result;
		
		result = new ModelAndView("message/create");
		result.addObject("formAction", "message/actor/reply.do");
		result.addObject("sendMessage", msg);
		result.addObject("message", text);
		result.addObject("isReply", true);
		
		return result;
	}
	
}
