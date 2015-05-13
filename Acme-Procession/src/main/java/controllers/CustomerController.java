/* CustomerController.java
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherService;
import services.CustomerService;
import services.ViewerService;
import domain.Brother;
import domain.Customer;
import domain.Viewer;
import forms.RegistrationForm;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	// Services ---------------------------------------------------------------
	
	@Autowired
	private BrotherService brotherService;
	@Autowired
	private ViewerService viewerService;
	@Autowired
	private CustomerService customerService;
	
	// Constructors -----------------------------------------------------------
	
	public CustomerController() {
		super();
	}
	
	// Listing ----------------------------------------------------------------
	
	
	// Creation ---------------------------------------------------------------
	
	// Edition ----------------------------------------------------------------	
	
	// Details ----------------------------------------------------------------
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int customerId) {
		ModelAndView result;
		Customer customer;

		customer = customerService.findOne(customerId);	
		
		result = new ModelAndView("customer/details");
		result.addObject("customer", customer);

		return result;
	}
	
	// Register ---------------------------------------------------------------

	@RequestMapping(value = "/registerBrother", method = RequestMethod.GET)
	public ModelAndView registerBrother() {
		ModelAndView result;
		RegistrationForm registrationForm;
		
		registrationForm = new RegistrationForm();

		result = createEditModelAndView(registrationForm);
		result.addObject("action", "customer/registerBrother.do");

		return result;
	}

	@RequestMapping(value = "/registerViewer", method = RequestMethod.GET)
	public ModelAndView registerViewer() {
		ModelAndView result;
		RegistrationForm registrationForm;
		
		registrationForm = new RegistrationForm();

		result = createEditModelAndView(registrationForm);
		result.addObject("action", "customer/registerViewer.do");

		return result;
	}

	@RequestMapping(value = "/registerBrother", method = RequestMethod.POST, params = "save")
	public ModelAndView saveBrother(@Valid RegistrationForm registrationForm,
			BindingResult binding) {
		ModelAndView result;
		Brother brother;

		if (binding.hasErrors()) {
			result = createEditModelAndView(registrationForm);
			result.addObject("action", "customer/registerBrother.do");
		} else {
			try {
				customerService.checkPassword(registrationForm);
				brother = brotherService.create();
				brother = (Brother) customerService.convertToCustomer(brother, registrationForm);
				brotherService.registerToTheSystem(brother);
				
				
				result = new ModelAndView("redirect:welcome.do");
				
			}catch(IllegalArgumentException exp){
					if(exp.getMessage().equals("passwords dont match")){
						result = createEditModelAndView(registrationForm,"customer.passDuplicate.error");
						result.addObject("action", "customer/registerBrother.do");
					}else if(exp.getMessage().equals("credit card expired")){
						result = createEditModelAndView(registrationForm,"customer.creditCardExpired.error");
						result.addObject("action", "customer/registerBrother.do");
					}else {
						result = createEditModelAndView(registrationForm,"customer.commit.error");
						result.addObject("action", "customer/registerBrother.do");
					}
			} catch (Throwable oops) {
				if(oops instanceof DataIntegrityViolationException){
					result = createEditModelAndView(registrationForm, "customer.duplicatedUser.error");
					result.addObject("action", "customer/registerBrother.do");
				}else{
					result = createEditModelAndView(registrationForm, "customer.commit.error");
					result.addObject("action", "customer/registerBrother.do");
				}
			}
		}

		return result;
	}

	@RequestMapping(value = "/registerViewer", method = RequestMethod.POST, params = "save")
	public ModelAndView saveViewer(@Valid RegistrationForm registrationForm,
			BindingResult binding) {
		ModelAndView result;
		Viewer viewer;

		if (binding.hasErrors()) {
			result = createEditModelAndView(registrationForm);
			result.addObject("action", "customer/registerViewer.do");
		} else {
			try {
				customerService.checkPassword(registrationForm);
				viewer = viewerService.create();
				viewer = (Viewer) customerService.convertToCustomer(viewer,registrationForm);
				viewerService.registerToTheSystem(viewer);
				
				
				result = new ModelAndView("redirect:welcome.do");
			
			}catch(IllegalArgumentException exp){
				if(exp.getMessage().equals("passwords dont match")){
					result = createEditModelAndView(registrationForm,"customer.passDuplicate.error");
					result.addObject("action", "customer/registerViewer.do");
				}else if(exp.getMessage().equals("credit card expired")){
					result = createEditModelAndView(registrationForm,"customer.creditCardExpired.error");
					result.addObject("action", "customer/registerViewer.do");
				}else{
					result = createEditModelAndView(registrationForm,"customer.commit.error");
					result.addObject("action", "customer/registerViewer.do");
				}
			} catch (Throwable oops) {
				if(oops instanceof DataIntegrityViolationException){
					result = createEditModelAndView(registrationForm, "customer.duplicatedUser.error");
					result.addObject("action", "customer/registerViewer.do");
				}else{
					result = createEditModelAndView(registrationForm, "customer.commit.error");
					result.addObject("action", "customer/registerViewer.do");
				}
			}
		}

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(
			RegistrationForm registrationForm) {
		ModelAndView result;

		result = createEditModelAndView(registrationForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(
			RegistrationForm registrationForm, String message) {
		ModelAndView result;

		result = new ModelAndView("customer/register");
		result.addObject("registrationForm", registrationForm);
		result.addObject("message", message);

		return result;
	}
}