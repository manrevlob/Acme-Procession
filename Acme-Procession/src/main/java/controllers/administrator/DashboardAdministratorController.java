package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherService;
import services.BrotherhoodService;
import services.ProcessionService;
import services.ViewerService;

import controllers.AbstractController;
import domain.Brother;
import domain.Brotherhood;
import domain.Procession;
import domain.Viewer;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Services-----------------------------------------------------------------

	@Autowired
	private BrotherhoodService brotherhoodService;
	@Autowired
	private ProcessionService processionService;
	@Autowired
	private BrotherService brotherService;
	@Autowired
	private ViewerService viewerService;

	// Constructors -----------------------------------------------------------

	public DashboardAdministratorController() {
		super();
	}

	// List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		String uri;

		uri = "dashboard/administrator/list.do";
		result = new ModelAndView("dashboard/list");

		result.addObject("requestURI", uri);

		Collection<Brotherhood> findAllBrotHOrderByNumReg;
		Collection<Procession> findAllProceOrderByNumReg;
		Collection<Brother> findByBrotherhoodAndBrother;
		Collection<Viewer> findAllReserMorBox;
		Collection<Viewer> findAllOrderByNumAssess;
		Collection<Brother> findByNumBrotherhood;
		Collection<Object[]> findAllTotalCostOfRegistration;
		Collection<Object[]> findAllTotalCostOfCostume;
		Collection<Object[]> findAllByAssess;
		Collection<Brother> findWithAutoAndCostumePay;

		findAllBrotHOrderByNumReg = brotherhoodService.findAllOrderByNumReg();
		findAllProceOrderByNumReg = processionService.findAllOrderByNumReg();
		findByBrotherhoodAndBrother = brotherService.findByBrotherhoodAndBrother();
		findAllReserMorBox = viewerService.findAllReserMorBox();
		findAllOrderByNumAssess = viewerService.findAllOrderByNumAssess();
		findByNumBrotherhood = brotherService.findByNumBrotherhood();
		findAllTotalCostOfRegistration = brotherService.findAllTotalCostOfRegistration();
		findAllTotalCostOfCostume = brotherService.findAllTotalCostOfCostume();
		findAllByAssess = brotherhoodService.findAllByAssess();
		findWithAutoAndCostumePay = brotherService.findWithAutoAndCostumePay();
		
		result.addObject("findAllBrotHOrderByNumReg", findAllBrotHOrderByNumReg);
		result.addObject("findAllProceOrderByNumReg", findAllProceOrderByNumReg);
		result.addObject("findByBrotherhoodAndBrother", findByBrotherhoodAndBrother);
		result.addObject("findAllReserMorBox", findAllReserMorBox);
		result.addObject("findAllOrderByNumAssess", findAllOrderByNumAssess);
		result.addObject("findByNumBrotherhood", findByNumBrotherhood);
		result.addObject("findAllTotalCostOfRegistration", findAllTotalCostOfRegistration);
		result.addObject("findAllTotalCostOfCostume", findAllTotalCostOfCostume);
		result.addObject("findAllByAssess", findAllByAssess);
		result.addObject("findWithAutoAndCostumePay", findWithAutoAndCostumePay);

		return result;
	}

}
