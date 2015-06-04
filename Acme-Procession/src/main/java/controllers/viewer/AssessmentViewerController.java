package controllers.viewer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AssessmentService;
import services.ProcessionService;
import controllers.AbstractController;
import domain.Assessment;
import domain.Procession;

@Controller
@RequestMapping("/assessment/viewer")
public class AssessmentViewerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AssessmentService assessmentService;
	@Autowired
	private ProcessionService processionService;

	// Constructors -----------------------------------------------------------

	public AssessmentViewerController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Assessment> assessments;
		String uri;

		assessments = assessmentService.findAllByViewer();
		uri = "assessment/viewer/list.do";

		result = new ModelAndView("assessment/list");
		result.addObject("requestURI", uri);
		result.addObject("assessments", assessments);

		return result;
	}

	// AssessById--------------------------------------------------------------

	@RequestMapping(value = "/assessById", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int processionId) {
		ModelAndView result;
		Assessment assessment;
		Procession procession;

		assessment = assessmentService.create();

		procession = processionService.findOne(processionId);

		assessment.setProcession(procession);

		result = createByIdModelAndView(assessment);

		return result;
	}

	@RequestMapping(value = "/assessById", method = RequestMethod.POST, params = "save")
	public ModelAndView saveById(@Valid Assessment assessment,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createByIdModelAndView(assessment);
		} else {
			try {
				assessmentService.save(assessment);
				result = new ModelAndView("redirect:/assessment/viewer/list.do");
			} catch (Throwable oops) {
				result = createByIdModelAndView(assessment,
						"brotherhood.commit.error");
			}
		}
		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Assessment assessment;

		assessment = assessmentService.create();

		result = createEditModelAndView(assessment);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int assessmentId) {
		ModelAndView result;
		Assessment assessment;
		Collection<Procession> processions;

		assessment = assessmentService.findOne(assessmentId);
		processions = processionService.findAll();

		result = createEditModelAndView(assessment);
		result.addObject("processions", processions);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Assessment assessment, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(assessment);
		} else {
			try {
				assessmentService.save(assessment);
				result = new ModelAndView("redirect:/assessment/viewer/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(assessment,
						"assessment.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createByIdModelAndView(Assessment assessment) {
		ModelAndView result;

		result = createByIdModelAndView(assessment, null);

		return result;
	}

	protected ModelAndView createByIdModelAndView(Assessment assessment,
			String message) {
		ModelAndView result;

		result = new ModelAndView("assessment/assessById");
		result.addObject("assessment", assessment);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createEditModelAndView(Assessment assessment) {
		ModelAndView result;

		result = createEditModelAndView(assessment, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Assessment assessment,
			String message) {
		ModelAndView result;
		Collection<Procession> processions;

		processions = processionService.findAll();

		result = new ModelAndView("assessment/create");
		result.addObject("processions", processions);
		result.addObject("assessment", assessment);
		result.addObject("message", message);

		return result;
	}

}
