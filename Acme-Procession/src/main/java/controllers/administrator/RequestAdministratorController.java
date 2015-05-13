package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RequestService;
import controllers.AbstractController;
import domain.Request;
import forms.AddCommentToRequestForm;

@Controller
@RequestMapping("/request/administrator")
public class RequestAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private RequestService requestService;

	// Constructors -----------------------------------------------------------

	public RequestAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Request> requests;

		requests = requestService.findAllRequestPending();

		result = new ModelAndView("request/list");
		result.addObject("requestURI", "request/administrator/list.do");
		result.addObject("requests", requests);

		return result;
	}

	// ManageRequest -------------------------------------------------------

	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam int requestId) {
		ModelAndView result;
		AddCommentToRequestForm addCommentToRequestForm;
		Request request;

		addCommentToRequestForm = new AddCommentToRequestForm();

		request = requestService.findOne(requestId);
		addCommentToRequestForm.setRequest(request);

		result = createEditModelAndView(addCommentToRequestForm);
		result.addObject("requestURI", "request/administrator/acceptRequest.do");
		result.addObject("status", "Accept");

		return result;

	}

	@RequestMapping(value = "/acceptRequest", method = RequestMethod.POST, params = "save")
	public ModelAndView acceptRequest(
			@Valid AddCommentToRequestForm addCommentToRequestForm) {
		ModelAndView result;
		Collection<Request> requests;

		try {
			requestService.acceptRequest(addCommentToRequestForm);

			requests = requestService.findAllRequestPending();

			result = new ModelAndView("request/list");
			result.addObject("requestURI", "request/administrator/list.do");
			result.addObject("requests", requests);
		} catch (Throwable oops) {

			result = createEditModelAndView(addCommentToRequestForm);
			result.addObject("requestURI",
					"request/administrator/acceptRequest.do");
			result.addObject("message", "request.commit.error");
			result.addObject("status", "Accept");

		}

		return result;

	}

	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam int requestId) {
		ModelAndView result;
		AddCommentToRequestForm addCommentToRequestForm;
		Request request;

		addCommentToRequestForm = new AddCommentToRequestForm();

		request = requestService.findOne(requestId);
		addCommentToRequestForm.setRequest(request);

		result = createEditModelAndView(addCommentToRequestForm);
		result.addObject("requestURI", "request/administrator/rejectRequest.do");
		result.addObject("status", "Reject");

		return result;

	}

	@RequestMapping(value = "/rejectRequest", method = RequestMethod.POST, params = "save")
	public ModelAndView rejectRequest(
			@Valid AddCommentToRequestForm addCommentToRequestForm) {
		ModelAndView result;
		Collection<Request> requests;

		try {
			requestService.rejectedRequest(addCommentToRequestForm);

			requests = requestService.findAllRequestPending();

			result = new ModelAndView("request/list");
			result.addObject("requestURI", "request/administrator/list.do");
			result.addObject("requests", requests);
		} catch (IllegalArgumentException exp) {
			if (exp.getMessage().equals("need comments")) {
				result = createEditModelAndView(addCommentToRequestForm);
				result.addObject("requestURI",
						"request/administrator/rejectedRequest.do");
				result.addObject("message", "request.comment.error");
				result.addObject("status", "Reject");
			} else {
				result = createEditModelAndView(addCommentToRequestForm);
				result.addObject("requestURI",
						"request/administrator/rejectedRequest.do");
				result.addObject("message", "request.commit.error");
				result.addObject("status", "Reject");
			}

		} catch (Throwable oops) {

			result = createEditModelAndView(addCommentToRequestForm);
			result.addObject("requestURI",
					"request/administrator/rejectedRequest.do");
			result.addObject("message", "request.commit.error");
			result.addObject("status", "Reject");
		}

		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(
			AddCommentToRequestForm addCommentToRequestForm) {
		ModelAndView result;

		result = createEditModelAndView(addCommentToRequestForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(
			AddCommentToRequestForm addCommentToRequestForm, String message) {
		ModelAndView result;

		result = new ModelAndView("request/addComment");
		result.addObject("addCommentToRequestForm", addCommentToRequestForm);
		result.addObject("message", message);

		return result;
	}
}