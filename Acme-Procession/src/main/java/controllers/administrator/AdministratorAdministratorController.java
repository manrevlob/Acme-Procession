package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.AdministratorService;
import domain.Administrator;
import forms.RegistrationAdminForm;

@Controller
@RequestMapping("/administrator/administrator")
public class AdministratorAdministratorController extends AbstractController{
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private AdministratorService administratorService;
	
	// Constructors -----------------------------------------------------------
	
	public AdministratorAdministratorController() {
		super();
	}
	
	// Listing ----------------------------------------------------------------
	
	// Creation ---------------------------------------------------------------
	
	// Edition ----------------------------------------------------------------	
	
	// Register ---------------------------------------------------------------

	@RequestMapping(value = "/registerAdministrator", method = RequestMethod.GET)
	public ModelAndView registerAdministrator() {
		ModelAndView result;
		RegistrationAdminForm registrationAdminForm;
		
		registrationAdminForm = new RegistrationAdminForm();

		result = createEditModelAndView(registrationAdminForm);
		result.addObject("action", "administrator/administrator/registerAdministrator.do");

		return result;
	}

	@RequestMapping(value = "/registerAdministrator", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAdministrator(@Valid RegistrationAdminForm registrationAdminForm,
			BindingResult binding) {
		ModelAndView result;
		Administrator administrator;

		if (binding.hasErrors()) {
			result = createEditModelAndView(registrationAdminForm);
			result.addObject("action", "administrator/administrator/registerAdministrator.do");
		} else { 
			try {
				administratorService.checkPassword(registrationAdminForm);
				
				administrator = administratorService.create();
				administrator = (Administrator) administratorService.convertToAdministrator(administrator, registrationAdminForm);
				administratorService.save(administrator);
				
				result = new ModelAndView("redirect:/");
				
			} catch(IllegalArgumentException exp){
				
				result = createEditModelAndView(registrationAdminForm,"administrator.passDuplicate");
				result.addObject("action", "administrator/administrator/registerAdministrator.do");
			}
			catch (Throwable oops) {
				if(oops instanceof DataIntegrityViolationException){
					result = createEditModelAndView(registrationAdminForm, "administrator.duplicatedUser");
					result.addObject("action", "administrator/administrator/registerAdministrator.do");
				}else{
					result = createEditModelAndView(registrationAdminForm, "administrator.commit.error");
					result.addObject("action", "administrator/administrator/registerAdministrator.do");
				}
			}
		}

		return result;
	}

	
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(
			RegistrationAdminForm registrationAdminForm) {
		ModelAndView result;

		result = createEditModelAndView(registrationAdminForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(
			RegistrationAdminForm registrationAdminForm, String message) {
		ModelAndView result;

		result = new ModelAndView("administrator/register");
		result.addObject("registrationAdminForm", registrationAdminForm);
		result.addObject("message", message);

		return result;
	}

}
