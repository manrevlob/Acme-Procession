package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.BrotherService;
import services.BrotherhoodService;
import services.ProcessionService;
import domain.Procession;

@Controller
@RequestMapping("/procession")
public class ProcessionController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ProcessionService processionService;

	@Autowired
	private BrotherhoodService brotherhoodService;

	@Autowired
	private BrotherService brotherService;

	@Autowired
	private ActorService actorService;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public ProcessionController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int brotherhoodId) {
		ModelAndView result;
		Collection<Procession> processions;
		boolean isBigBrother;
		String uri;

		processions = processionService.findByBrotherhood(brotherhoodId);
		try {
			isBigBrother = LoginService.getPrincipal() != null
					&& actorService.isBrother()
					&& brotherhoodService.findOne(brotherhoodId).getBigBrothers()
							.contains(brotherService.findByPrincipal());
		} catch (Throwable opps) {
			isBigBrother = false;
		}
		uri = "procession/list.do";

		result = new ModelAndView("procession/list");
		result.addObject("processions", processions);
		result.addObject("isBigBrother", isBigBrother);
		result.addObject("requestURI", uri);

		return result;
	}

	// Details -----------------------------------------------------------------

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int processionId) {
		ModelAndView result;
		Procession procession;

		procession = processionService.findOne(processionId);

		result = new ModelAndView("procession/details");
		result.addObject("procession", procession);

		return result;
	}

	// Create -----------------------------------------------------------------

	// Edit -------------------------------------------------------------------

	// Ancillary methods ------------------------------------------------------

}
