package controllers.bigBrother;

import java.sql.SQLException;
import java.util.Collection;

import javax.sql.rowset.serial.SerialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.ImageService;
import services.ProcessionService;
import services.StretchService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Image;
import domain.Procession;
import domain.Stretch;
import forms.AddImageToProcessionForm;
import forms.AddStretchToProcessionForm;

@Controller
@RequestMapping("/procession/bigBrother")
public class ProcessionBigBrotherController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ProcessionService processionService;

	// Supporting services ----------------------------------------------------

	@Autowired
	private BrotherhoodService brotherhoodService;
	@Autowired
	private StretchService stretchService;
	@Autowired
	private ImageService imageService;

	// Constructors -----------------------------------------------------------

	public ProcessionBigBrotherController() {
		super();
	}

	// Listing ----------------------------------------------------------------

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
						"redirect:/procession/list.do?brotherhoodId="
								+ procession.getBrotherhood().getId());
			} catch (Throwable oops) {
				if (oops.getMessage().equals("procession.date.error")) {
					result = createEditModelAndView(procession,
							"procession.date.error");
				} else {
					if (oops.getMessage().equals("procession.dateInPast.error")) {
						result = createEditModelAndView(procession,
								"procession.dateInPast.error");
					} else {
						result = createEditModelAndView(procession,
								"procession.commit.error");
					}
				}
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
						"redirect:/procession/list.do?brotherhoodId="
								+ procession.getBrotherhood().getId());
			} catch (Throwable oops) {
				if(oops.getMessage().equals("procession.oneOrMoreRegisters.error")) {
					result = createEditModelAndView(procession,
							"procession.oneOrMoreRegisters.error");
				} else {
					result = createEditModelAndView(procession,
							"procession.commit.error");
				}
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
						"redirect:/stretchOrder/list.do?processionId="
								+ addStretchToProcessionForm.getProcession()
										.getId());
			} catch (Throwable oops) {
				result = new ModelAndView("procession/addStretch");
				if (oops.getMessage().equals(
						"procession.duplicatedCarving.error")) {
					result.addObject("message",
							"procession.duplicatedCarving.error");
				} else {
					result.addObject("message", "procession.commit.error");
				}
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

	// Image ------------------------------------------------------------------

	@RequestMapping(value = "/uploadImage", method = RequestMethod.GET)
	public ModelAndView get(@RequestParam int processionId) {
		Image image;
		Procession procession;
		AddImageToProcessionForm addImageToProcessionForm;

		procession = processionService.findOneIfPrincipal(processionId);

		addImageToProcessionForm = new AddImageToProcessionForm();
		image = new Image();

		addImageToProcessionForm.setImage(image);
		addImageToProcessionForm.setProcession(procession);

		return new ModelAndView("procession/uploadImage").addObject(
				"addImageToProcessionForm", addImageToProcessionForm);
	}

	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	public ModelAndView uploadFileHandler(
			@Valid AddImageToProcessionForm addImageToProcessionForm,
			BindingResult binding) throws SerialException, SQLException {
		ModelAndView result;
		Procession procession;
		Image image;

		procession = addImageToProcessionForm.getProcession();

		try {
			imageService.checkImage(addImageToProcessionForm.getImage());
			image = imageService.save(addImageToProcessionForm.getImage());

			procession.setImage(image);
			processionService.save(procession);

			result = new ModelAndView(
					"redirect:/brotherhood/bigBrother/listOwns.do");
		} catch (Throwable oops) {
			result = uploadModelAndView(addImageToProcessionForm,
					"commit.image.error");
		}

		return result;
	}

	@RequestMapping(value = "/viewImage", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam int processionId) {
		Image image;
		Procession procession;

		procession = processionService.findOne(processionId);

		image = procession.getImage();

		return new ModelAndView("procession/viewImage").addObject("image",
				image);
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView uploadModelAndView(
			AddImageToProcessionForm addImageToProcessionForm, String message) {
		ModelAndView result;

		result = new ModelAndView("procession/uploadImage");
		result.addObject("addImageToProcessionForm", addImageToProcessionForm);
		result.addObject("message", message);

		return result;
	}

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
