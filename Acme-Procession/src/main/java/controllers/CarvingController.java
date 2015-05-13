package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CarvingService;
import domain.Carving;

@Controller
@RequestMapping("/carving")
public class CarvingController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CarvingService carvingService;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public CarvingController() {
		super();
	}

	// Listing ----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView info(@RequestParam int brotherhoodId) {
		ModelAndView result;
		Collection<Carving> carvings;

		carvings = carvingService.findAllByBrotherhood(brotherhoodId);

		result = new ModelAndView("carving/list");
		result.addObject("carvings", carvings);

		return result;
	}

	// Create -----------------------------------------------------------------

	// Edit -------------------------------------------------------------------

	// Ancillary methods ------------------------------------------------------

}
