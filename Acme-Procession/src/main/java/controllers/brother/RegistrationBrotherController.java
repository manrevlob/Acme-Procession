package controllers.brother;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherService;
import services.RegistrationService;
import controllers.AbstractController;
import domain.Registration;

@Controller
@RequestMapping("/registration/brother")
public class RegistrationBrotherController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private RegistrationService registrationService;

	// Supporting services ----------------------------------------------------

	@Autowired
	private BrotherService brotherService;

	// Constructors -----------------------------------------------------------

	public RegistrationBrotherController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Registration> registrations;
		int brotherId;
		String uri;

		brotherId = brotherService.findByPrincipal().getId();

		registrations = registrationService.findAllByBrother(brotherId);
		uri = "registration/brother/list.do";

		result = new ModelAndView("registration/list");
		result.addObject("registrations", registrations);
		result.addObject("requestURI", uri);

		return result;
	}

}
