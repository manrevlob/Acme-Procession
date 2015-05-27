package controllers.brother;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CostumeReserveService;
import services.CostumeService;
import domain.Costume;
import domain.CostumeReserve;

@Controller
@RequestMapping("/costumeReserve/brother")
public class CostumeReserveBrotherController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CostumeReserveService costumeReserveService;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CostumeService costumeService;

	// Constructors -----------------------------------------------------------

	public CostumeReserveBrotherController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<CostumeReserve> costumeReserves;
		String uri;

		costumeReserves = costumeReserveService.findByPrincipal();
		uri = "costumeReserve/brother/list.do";

		result = new ModelAndView("costumeReserve/list");
		result.addObject("costumeReserves", costumeReserves);
		result.addObject("requestURI", uri);

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int costumeId) {
		ModelAndView result;
		CostumeReserve costumeReserve;
		Costume costume;

		try {
			costume = costumeService.findOneIfAvailable(costumeId);
			costumeReserve = costumeReserveService.create(costume);
			costumeReserveService.save(costumeReserve);
			result = new ModelAndView("redirect:/costumeReserve/brother/list.do");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/costumeReserve/brother/list.do");
		}

		return result;
	}

	// Edition ----------------------------------------------------------------

	// Details ----------------------------------------------------------------

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int costumeReserveId) {
		ModelAndView result;
		CostumeReserve costumeReserve;

		costumeReserve = costumeReserveService.findOneIfPrincipal(costumeReserveId);

		result = new ModelAndView("costumeReserve/details");
		result.addObject("costumeReserve", costumeReserve);

		return result;
	}

	// Ancillary methods ------------------------------------------------------

}
