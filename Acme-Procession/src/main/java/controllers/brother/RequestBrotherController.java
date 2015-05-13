package controllers.brother;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RequestService;
import controllers.AbstractController;
import domain.Request;

@Controller
@RequestMapping("/request/brother")
public class RequestBrotherController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private RequestService requestService;

	// Constructors -----------------------------------------------------------

	public RequestBrotherController() {
		super();
	}

	// Listing ----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Request> requests;
		Collection<String> statusColl;
		String status;

		status = "";

		requests = requestService.findByPrincipal();
		statusColl = requestService.AuthBrother();

		if (statusColl.contains("pending") || statusColl.contains("accepted")) {
			status = "";
		} else {
			status = "rejected";
		}

		result = new ModelAndView("request/list");
		result.addObject("requestURI", "request/brother/list.do");
		result.addObject("status", status);
		result.addObject("requests", requests);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Request request;

		request = requestService.create();

		result = createEditModelAndView(request);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Request request, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(request);
		} else {
			try {
				requestService.saveRequest(request);
				result = new ModelAndView(
						"redirect:/request/brother/list.do");
			} catch (Throwable oops) {
				if (oops.getMessage().equals("request.comment.error")) {
					result = createEditModelAndView(request,
							"request.comment.error");
				} else if (oops.getMessage().equals("request.pending.error")) {
					result = createEditModelAndView(request,
							"request.pending.error");
				} else if (oops.getMessage().equals("request.accepted.error")) {
					result = createEditModelAndView(request,
							"request.accepted.error");
				} else if (oops.getMessage().equals("request.notPending.error")) {
					result = createEditModelAndView(request,
							"request.notPending.error");
				} else {
					result = createEditModelAndView(request,
							"request.commit.error");
				}

			}
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Request request) {
		ModelAndView result;

		result = createEditModelAndView(request, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Request request,
			String message) {
		ModelAndView result;

		result = new ModelAndView("request/create");
		result.addObject("request", request);
		result.addObject("message", message);

		return result;
	}

}
