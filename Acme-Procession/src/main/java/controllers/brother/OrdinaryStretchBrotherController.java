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
import services.OrdinaryStretchService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.OrdinaryStretch;

@Controller
@RequestMapping("/ordinaryStretch/brother")
public class OrdinaryStretchBrotherController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private OrdinaryStretchService ordinaryStretchService;
	@Autowired
	private BrotherhoodService brotherhoodService;

	// Constructors -----------------------------------------------------------

	public OrdinaryStretchBrotherController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/listOwns", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<OrdinaryStretch> ordinaryStretches;
		String uri;

		ordinaryStretches = ordinaryStretchService.findMines();
		uri = "ordinaryStretch/brother/listOwns.do";

		result = new ModelAndView("ordinaryStretch/list");
		result.addObject("requestURI", uri);
		result.addObject("ordinaryStretches", ordinaryStretches);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		OrdinaryStretch ordinaryStretch;

		ordinaryStretch = ordinaryStretchService.create();

		result = createEditModelAndView(ordinaryStretch);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int ordinaryStretchId) {
		ModelAndView result;
		OrdinaryStretch ordinaryStretch;

		ordinaryStretch = ordinaryStretchService
				.findOneIfPrincipal(ordinaryStretchId);
		result = createEditModelAndView(ordinaryStretch);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid OrdinaryStretch ordinaryStretch,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(ordinaryStretch);
		} else {
			try {
				ordinaryStretchService.save(ordinaryStretch);
				result = new ModelAndView(
						"redirect:/ordinaryStretch/brother/listOwns.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(ordinaryStretch,
						"stretch.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid OrdinaryStretch ordinaryStretch,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(ordinaryStretch);
		} else {
			try {
				ordinaryStretchService.delete(ordinaryStretch);
				result = new ModelAndView(
						"redirect:/ordinaryStretch/brother/listOwns.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(ordinaryStretch,
						"stretch.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(
			OrdinaryStretch ordinaryStretch) {
		ModelAndView result;

		result = createEditModelAndView(ordinaryStretch, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(
			OrdinaryStretch ordinaryStretch, String message) {
		ModelAndView result;
		Collection<Brotherhood> brotherhoods;

		brotherhoods = brotherhoodService.findOwns();

		result = new ModelAndView("ordinaryStretch/edit");
		result.addObject("ordinaryStretch", ordinaryStretch);
		result.addObject("message", message);
		result.addObject("brotherhoods", brotherhoods);

		return result;
	}

}
