package controllers.brother;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ProcessionService;
import controllers.AbstractController;
import domain.Procession;

@Controller
@RequestMapping("/procession/brother")
public class ProcessionBrotherController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ProcessionService processionService;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public ProcessionBrotherController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/listAvailables", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		boolean isBigBrother;
		Collection<Procession> processions;
		String uri;

		processions = processionService.findAllAvailables();
		isBigBrother = false;
		uri = "procession/brother/listAvailables.do";

		result = new ModelAndView("procession/list");
		result.addObject("processions", processions);
		result.addObject("isBigBrother", isBigBrother);
		result.addObject("uri", uri);

		return result;
	}

	// Create -----------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	// Ancillary methods ------------------------------------------------------

}
