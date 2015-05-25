package controllers.bigBrother;

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
import services.CarvingService;
import services.FloatStretchService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Carving;
import domain.FloatStretch;

@Controller
@RequestMapping("/floatStretch/bigBrother")
public class FloatStretchBigBrotherController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private FloatStretchService floatStretchService;

	@Autowired
	private BrotherhoodService brotherhoodService;

	@Autowired
	private CarvingService carvingService;

	// Constructors -----------------------------------------------------------

	public FloatStretchBigBrotherController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int brotherhoodId) {
		ModelAndView result;
		Collection<FloatStretch> floatStretches;
		Brotherhood brotherhood;
		String uri;

		brotherhood = brotherhoodService.findOneIfPrincipal(brotherhoodId);
		floatStretches = floatStretchService.findByBrotherhood(brotherhood);
		uri = "floatStretch/bigBrother/listOwns.do";

		result = new ModelAndView("floatStretch/list");
		result.addObject("requestURI", uri);
		result.addObject("floatStretches", floatStretches);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int brotherhoodId) {
		ModelAndView result;
		FloatStretch floatStretch;
		Brotherhood brotherhood;

		brotherhood = brotherhoodService.findOneIfPrincipal(brotherhoodId);
		floatStretch = floatStretchService.create(brotherhood);

		result = createEditModelAndView(floatStretch);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int floatStretchId) {
		ModelAndView result;
		FloatStretch floatStretch;

		floatStretch = floatStretchService.findOneIfPrincipal(floatStretchId);
		result = createEditModelAndView(floatStretch);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid FloatStretch floatStretch,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(floatStretch);
		} else {
			try {
				floatStretchService.save(floatStretch);
				result = new ModelAndView(
						"redirect:/floatStretch/bigBrother/list.do?brotherhoodId="
								+ floatStretch.getBrotherhood().getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(floatStretch,
						"stretch.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid FloatStretch floatStretch,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(floatStretch);
		} else {
			try {
				floatStretchService.delete(floatStretch);
				result = new ModelAndView(
						"redirect:/brotherhood/bigBrother/listOwns.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(floatStretch,
						"stretch.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(FloatStretch floatStretch) {
		ModelAndView result;

		result = createEditModelAndView(floatStretch, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(FloatStretch floatStretch,
			String message) {
		ModelAndView result;
		Collection<Carving> carvings;

		carvings = carvingService.findByBrotherhood(floatStretch.getBrotherhood().getId());

		result = new ModelAndView("floatStretch/edit");
		result.addObject("floatStretch", floatStretch);
		result.addObject("message", message);
		result.addObject("carvings", carvings);

		return result;
	}

}
