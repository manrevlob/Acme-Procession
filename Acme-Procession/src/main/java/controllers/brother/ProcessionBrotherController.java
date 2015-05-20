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

import services.BrotherService;
import services.BrotherhoodService;
import services.ProcessionService;
import services.StretchService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Procession;
import domain.Stretch;
import forms.AddStretchToProcessionForm;

@Controller
@RequestMapping("/procession/brother")
public class ProcessionBrotherController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ProcessionService processionService;

	// Supporting services ----------------------------------------------------

	@Autowired
	private BrotherhoodService brotherhoodService;

	@Autowired
	private StretchService stretchService;

	@Autowired
	private BrotherService brotherService;

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
		isBigBrother = brotherService.findByPrincipal().getIsBigBrother();
		uri = "procession/brother/listAvailables.do";

		result = new ModelAndView("procession/list");
		result.addObject("processions", processions);
		result.addObject("isBigBrother", isBigBrother);
		result.addObject("uri", uri);

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int brotherhoodId) {
		ModelAndView result;
		Procession procession;
		Brotherhood brotherhood;

		brotherhood = brotherhoodService.findOneIfPrincipal(brotherhoodId);

		procession = processionService.create(brotherhood);

		result = createEditModelAndView(procession);

		return result;
	}

	// Edit -------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int processionId) {
		ModelAndView result;
		Procession procession;

		procession = processionService.findOneIfPrincipal(processionId);
		result = createEditModelAndView(procession);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Procession procession, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(procession);
		} else {
			try {
				processionService.save(procession);
				result = new ModelAndView(
						"redirect:/brotherhood/brother/listOwns.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(procession,
						"procession.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid Procession procession,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(procession);
		} else {
			try {
				processionService.delete(procession);
				result = new ModelAndView(
						"redirect:/brotherhood/brother/listOwns.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(procession,
						"procession.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/addStretch", method = RequestMethod.GET)
	public ModelAndView addStretch(@RequestParam int processionId) {
		ModelAndView result;
		AddStretchToProcessionForm addStretchToProcessionForm;
		Collection<Stretch> availableStretches;
		Procession procession;

		procession = processionService.findOneIfPrincipal(processionId);
		availableStretches = stretchService.findAvailables(procession);

		addStretchToProcessionForm = new AddStretchToProcessionForm();
		addStretchToProcessionForm.setProcession(procession);

		result = new ModelAndView("procession/addStretch");
		result.addObject("addStretchToProcessionForm",
				addStretchToProcessionForm);
		result.addObject("availableStretches", availableStretches);

		return result;
	}

	@RequestMapping(value = "/addStretch", method = RequestMethod.POST, params = "addStretch")
	public ModelAndView addStretchSave(
			@Valid AddStretchToProcessionForm addStretchToProcessionForm,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("procession/addStretch");
			result.addObject("addStretchToProcessionForm",
					addStretchToProcessionForm);
		} else {
			try {
				processionService.addStretch(addStretchToProcessionForm);
				result = new ModelAndView(
						"redirect:/brotherhood/brother/listOwns.do");
			} catch (Throwable oops) {
				result = new ModelAndView("procession/addStretch");
				result.addObject("message", "procession.commit.error");
				result.addObject("addStretchToProcessionForm",
						addStretchToProcessionForm);
			}
		}

		return result;
	}

	@RequestMapping(value = "/close", method = RequestMethod.GET)
	public ModelAndView close(@RequestParam int processionId) {
		ModelAndView result;
		Procession procession;

		procession = processionService.findOneIfPrincipal(processionId);

		processionService.close(procession);

		result = new ModelAndView("redirect:/procession/list.do?brotherhoodId="
				+ procession.getBrotherhood().getId());

		return result;
	}

	@RequestMapping(value = "/open", method = RequestMethod.GET)
	public ModelAndView open(@RequestParam int processionId) {
		ModelAndView result;
		Procession procession;

		procession = processionService.findOneIfPrincipal(processionId);

		processionService.open(procession);

		result = new ModelAndView("redirect:/procession/list.do?brotherhoodId="
				+ procession.getBrotherhood().getId());

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Procession procession) {
		ModelAndView result;

		result = createEditModelAndView(procession, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Procession procession,
			String message) {
		ModelAndView result;
		Collection<Brotherhood> brotherhoods;

		brotherhoods = brotherhoodService.findOwns();

		result = new ModelAndView("procession/edit");
		result.addObject("procession", procession);
		result.addObject("message", message);
		result.addObject("brotherhoods", brotherhoods);

		return result;
	}

}
