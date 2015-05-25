package controllers.brother;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherService;
import services.BrotherhoodService;
import controllers.AbstractController;
import domain.Brother;
import domain.Brotherhood;

@Controller
@RequestMapping("/brotherhood/brother")
public class BrotherhoodBrotherController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private BrotherhoodService brotherhoodService;

	@Autowired
	private BrotherService brotherService;

	// Constructors -----------------------------------------------------------

	public BrotherhoodBrotherController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Brotherhood> brotherhoods;
		boolean isAuthorized;
		String uri;

		brotherhoods = brotherhoodService.findMines();
		isAuthorized = brotherService.findByPrincipal().getIsAuthorized();
		uri = "brotherhood/brother/list.do";

		result = new ModelAndView("brotherhood/list");
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("requestURI", uri);
		result.addObject("isAuthorized", isAuthorized);

		return result;
	}

	// Creation ---------------------------------------------------------------

	// Register ---------------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(@RequestParam int brotherhoodId) {
		ModelAndView result;
		Brotherhood brotherhood;
		Brother brother;
		String error;

		brotherhood = brotherhoodService.findOne(brotherhoodId);
		brother = brotherService.registerToBrotherhood(brotherhood);
		error = null;

		try {
			brotherService.save(brother);
			result = new ModelAndView("redirect:/brotherhood/brother/list.do");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/brotherhood/list.do");
			if (oops.getMessage().equals(
					"brotherhood.otherRegistrationCreated.error")) {
				error = "brotherhood.otherRegistrationCreated.error";
			} else {
				error = "brotherhood.commit.error";
			}
		}

		result.addObject("error", error);
		return result;
	}

	// Ancillary methods ------------------------------------------------------

}
