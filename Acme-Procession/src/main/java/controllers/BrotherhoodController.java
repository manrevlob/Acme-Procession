package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherService;
import services.BrotherhoodService;
import domain.Brotherhood;

@Controller
@RequestMapping("/brotherhood")
public class BrotherhoodController extends AbstractController {
	// Services ---------------------------------------------------------------

	@Autowired
	private BrotherhoodService brotherhoodService;

	@Autowired
	private BrotherService brotherService;

	// Constructors -----------------------------------------------------------

	public BrotherhoodController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Brotherhood> brotherhoods;
		boolean isAuthorized;
		String uri;

		brotherhoods = brotherhoodService.findAll();
		try {
			isAuthorized = brotherService.findByPrincipal().getIsAuthorized();
		} catch (Throwable oops) {
			isAuthorized = false;
		}
		uri = "brotherhood/list.do";

		result = new ModelAndView("brotherhood/list");
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("isAuthorized", isAuthorized);
		result.addObject("requestURI", uri);

		return result;
	}

	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	// Details -----------------------------------------------------------------

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int brotherhoodId) {
		ModelAndView result;
		Brotherhood brotherhood;

		brotherhood = brotherhoodService.findOne(brotherhoodId);

		result = new ModelAndView("brotherhood/details");
		result.addObject("brotherhood", brotherhood);

		return result;
	}

	// Ancillary methods ------------------------------------------------------

}
