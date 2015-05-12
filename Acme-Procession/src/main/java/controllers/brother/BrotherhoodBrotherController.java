package controllers.brother;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import controllers.AbstractController;
import domain.Brotherhood;

@Controller
@RequestMapping("/brotherhood/brother")
public class BrotherhoodBrotherController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private BrotherhoodService brotherhoodService;

	// Constructors -----------------------------------------------------------

	public BrotherhoodBrotherController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Brotherhood> brotherhoods;
		String uri;

		brotherhoods = brotherhoodService.findMines();
		uri = "brotherhood/brother/list.do";

		result = new ModelAndView("brotherhood/list");
		result.addObject("requestURI", uri);
		result.addObject("brotherhoods", brotherhoods);

		return result;
	}

	@RequestMapping(value = "/listOwns", method = RequestMethod.GET)
	public ModelAndView listOwns() {
		ModelAndView result;
		Collection<Brotherhood> brotherhoods;
		String uri;

		brotherhoods = brotherhoodService.findOwns();
		uri = "brotherhood/brother/listOwns.do";

		result = new ModelAndView("brotherhood/list");
		result.addObject("requestURI", uri);
		result.addObject("brotherhoods", brotherhoods);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Brotherhood brotherhood;

		brotherhood = brotherhoodService.create();

		result = createEditModelAndView(brotherhood);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int brotherhoodId) {
		ModelAndView result;
		Brotherhood brotherhood;

		brotherhood = brotherhoodService.findOne(brotherhoodId);
		result = createEditModelAndView(brotherhood);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Brotherhood brotherhood, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(brotherhood);
		} else {
			try {
				brotherhoodService.save(brotherhood);
				result = new ModelAndView("redirect:/brotherhood/brother/listOwns.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(brotherhood,
						"brotherhood.commit.error");
			}
		}

		return result;
	}

	// Details -----------------------------------------------------------------

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Brotherhood brotherhood) {
		ModelAndView result;

		result = createEditModelAndView(brotherhood, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Brotherhood brotherhood, String message) {
		ModelAndView result;

		result = new ModelAndView("brotherhood/edit");
		result.addObject("brotherhood", brotherhood);
		result.addObject("message", message);

		return result;
	}

}
