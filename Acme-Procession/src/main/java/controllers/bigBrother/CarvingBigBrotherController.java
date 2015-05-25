package controllers.bigBrother;

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
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Carving;

@Controller
@RequestMapping("/carving/bigBrother")
public class CarvingBigBrotherController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CarvingService carvingService;

	@Autowired
	private BrotherhoodService brotherhoodService;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public CarvingBigBrotherController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int brotherhoodId) {
		ModelAndView result;
		Carving carving;
		Brotherhood brotherhood;

		brotherhood = brotherhoodService.findOneIfPrincipal(brotherhoodId);

		carving = carvingService.create(brotherhood);

		result = createEditModelAndView(carving);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int carvingId) {
		ModelAndView result;
		Carving carving;

		carving = carvingService.findOne(carvingId);
		result = createEditModelAndView(carving);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Carving carving, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(carving);
		} else {
			try {
				carvingService.save(carving);
				result = new ModelAndView(
						"redirect:/brotherhood/bigBrother/listOwns.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(carving, "carving.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Carving carving) {
		ModelAndView result;

		result = createEditModelAndView(carving, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Carving carving,
			String message) {
		ModelAndView result;

		result = new ModelAndView("carving/edit");
		result.addObject("carving", carving);
		result.addObject("message", message);

		return result;
	}

}
