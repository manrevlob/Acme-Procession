package controllers.brother;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.CostumeService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Costume;

@Controller
@RequestMapping("/costume/brother")
public class CostumeBrotherController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CostumeService costumeService;

	// Supporting services ----------------------------------------------------

	@Autowired
	private BrotherhoodService brotherhoodService;

	// Constructors -----------------------------------------------------------

	public CostumeBrotherController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int brotherhoodId) {
		ModelAndView result;
		Collection<Costume> costumes;
		Brotherhood brotherhood;
		String uri;

		brotherhood = brotherhoodService.findOne(brotherhoodId);
		costumes = costumeService.findAvailablesByBrotherhood(brotherhood);
		uri = "costume/brother/list.do";

		result = new ModelAndView("costume/list");
		result.addObject("costumes", costumes);
		result.addObject("requestURI", uri);

		return result;
	}

	// Create -----------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	// Details ----------------------------------------------------------------
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int costumeId) {
		ModelAndView result;
		Costume costume;

		costume = costumeService.findOneIfAvailable(costumeId);

		result = new ModelAndView("costume/details");
		result.addObject("costume", costume);

		return result;
	}

}
