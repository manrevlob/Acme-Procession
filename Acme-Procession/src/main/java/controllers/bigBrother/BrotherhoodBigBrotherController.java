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

import services.BrotherService;
import services.BrotherhoodService;
import services.ImageService;
import controllers.AbstractController;
import domain.Brother;
import domain.Brotherhood;
import domain.Image;
import forms.AddBigBrotherForm;
import forms.AddImageToBrotherhoodForm;

@Controller
@RequestMapping("/brotherhood/bigBrother")
public class BrotherhoodBigBrotherController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private BrotherhoodService brotherhoodService;
	@Autowired
	private BrotherService brotherService;
	@Autowired
	private ImageService imageService;

	// Constructors -----------------------------------------------------------

	public BrotherhoodBigBrotherController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/listOwns", method = RequestMethod.GET)
	public ModelAndView listOwns() {
		ModelAndView result;
		boolean isAuthorized;
		Collection<Brotherhood> brotherhoods;
		String uri;

		brotherhoods = brotherhoodService.findOwns();
		isAuthorized = brotherService.findByPrincipal().getIsAuthorized();
		uri = "brotherhood/bigBrother/listOwns.do";

		result = new ModelAndView("brotherhood/list");
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("requestURI", uri);
		result.addObject("isAuthorized", isAuthorized);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Brotherhood brotherhood;

		brotherhood = brotherhoodService.create();

		result = createEditModelAndView(brotherhood);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int brotherhoodId) {
		ModelAndView result;
		Brotherhood brotherhood;

		brotherhood = brotherhoodService.findOne(brotherhoodId);
		result = createEditModelAndView(brotherhood);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Brotherhood brotherhood,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(brotherhood);
		} else {
			try {
				brotherhoodService.save(brotherhood);
				result = new ModelAndView(
						"redirect:/brotherhood/bigBrother/listOwns.do");
			} catch (Throwable oops) {
				if (oops.getMessage().equals("brotherhood.invalidYear.error")) {
					result = createEditModelAndView(brotherhood,
							"brotherhood.invalidYear.error");
				} else {
					result = createEditModelAndView(brotherhood,
							"brotherhood.commit.error");
				}
			}
		}

		return result;
	}

	@RequestMapping(value = "/addBigBrother", method = RequestMethod.GET)
	public ModelAndView addOrganiser(@RequestParam int brotherhoodId) {
		ModelAndView result;
		AddBigBrotherForm addBigBrotherForm;

		addBigBrotherForm = new AddBigBrotherForm();

		addBigBrotherForm.setBrotherhood(brotherhoodService
				.findOneIfPrincipal(brotherhoodId));
		result = addBrotherModelAndView(addBigBrotherForm);

		return result;
	}

	@RequestMapping(value = "/addBigBrother", method = RequestMethod.POST, params = "save")
	public ModelAndView addOrganiser(
			@Valid AddBigBrotherForm addBigBrotherForm, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = addBrotherModelAndView(addBigBrotherForm);
		} else {
			try {
				brotherhoodService.addBrother(addBigBrotherForm);
				result = new ModelAndView(
						"redirect:/brotherhood/bigBrother/listOwns.do");
			} catch (Throwable oops) {
				result = addBrotherModelAndView(addBigBrotherForm,
						"brotherhood.commit.error");
			}
		}

		return result;
	}

	// Image ------------------------------------------------------------------

	@RequestMapping(value = "/uploadImage", method = RequestMethod.GET)
	public ModelAndView get(@RequestParam int brotherhoodId) {
		Image image;
		Brotherhood brotherhood;
		AddImageToBrotherhoodForm addImageToBrotherhoodForm;

		brotherhood = brotherhoodService.findOneIfPrincipal(brotherhoodId);

		addImageToBrotherhoodForm = new AddImageToBrotherhoodForm();
		image = new Image();

		addImageToBrotherhoodForm.setImage(image);
		addImageToBrotherhoodForm.setBrotherhood(brotherhood);

		return new ModelAndView("brotherhood/uploadImage").addObject(
				"addImageToBrotherhoodForm", addImageToBrotherhoodForm);
	}

	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	public ModelAndView uploadFileHandler(
			@Valid AddImageToBrotherhoodForm addImageToBrotherhoodForm,
			BindingResult binding) throws SerialException, SQLException {
		ModelAndView result = null;
		Brotherhood brotherhood;
		Image image = null;

		brotherhood = addImageToBrotherhoodForm.getBrotherhood();

		try {
			imageService.checkImage(addImageToBrotherhoodForm.getImage());
			image = imageService.save(addImageToBrotherhoodForm.getImage());

			brotherhood.setLogo(image);
			brotherhoodService.save(brotherhood);

			result = new ModelAndView("redirect:/brotherhood/brother/list.do");
		} catch (Throwable oops) {
			result = uploadModelAndView(addImageToBrotherhoodForm, "commit.image.error");
		}

		return result;
	}

	@RequestMapping(value = "/viewImage", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam int brotherhoodId) {
		Image image;
		Brotherhood brotherhood;

		brotherhood = brotherhoodService.findOne(brotherhoodId);

		image = brotherhood.getLogo();

		return new ModelAndView("brotherhood/viewImage").addObject("image",
				image);
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView uploadModelAndView(
			AddImageToBrotherhoodForm addImageToBrotherhoodForm, String message) {
		ModelAndView result;

		result = new ModelAndView("brotherhood/uploadImage");
		result.addObject("addImageToBrotherhoodForm", addImageToBrotherhoodForm);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createEditModelAndView(Brotherhood brotherhood) {
		ModelAndView result;

		result = createEditModelAndView(brotherhood, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Brotherhood brotherhood,
			String message) {
		ModelAndView result;

		result = new ModelAndView("brotherhood/edit");
		result.addObject("brotherhood", brotherhood);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView addBrotherModelAndView(
			AddBigBrotherForm addBigBrotherForm) {
		ModelAndView result;

		result = addBrotherModelAndView(addBigBrotherForm, null);

		return result;
	}

	protected ModelAndView addBrotherModelAndView(
			AddBigBrotherForm addBigBrotherForm, String message) {
		ModelAndView result;
		Collection<Brother> brothers;

		brothers = brotherService.findAllBrothersNotAdded(addBigBrotherForm
				.getBrotherhood());

		result = new ModelAndView("brotherhood/addBigBrother");
		result.addObject("addBigBrotherForm", addBigBrotherForm);
		result.addObject("brothers", brothers);
		result.addObject("message", message);

		return result;
	}

}
