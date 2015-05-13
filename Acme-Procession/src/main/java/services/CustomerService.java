package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.LoginService;
import security.UserAccount;
import domain.CreditCard;
import domain.Customer;
import forms.RegistrationForm;

@Service
@Transactional
public class CustomerService {
	// Managed repository -----------------------------------------------------

	@Autowired
	private CustomerRepository customerRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public CustomerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Customer findOne(int customerId) {
		Customer result;

		result = customerRepository.findOne(customerId);

		return result;
	}

	public Collection<Customer> findAll() {
		Collection<Customer> result;

		result = customerRepository.findAll();

		return result;
	}

	// Other business methods -------------------------------------------------

	public Customer findByPrincipal() {
		Customer customer;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		customer = customerRepository.findByPrincipal(userAccount.getId());

		Assert.notNull(customer);

		return customer;
	}
	
	public Customer convertToCustomer(Customer customer,RegistrationForm registrationForm) {
		CreditCard creditCard;

		Assert.notNull(registrationForm);
		Assert.notNull(customer);

		creditCard = new CreditCard();
		creditCard.setHolderName(registrationForm.getHolderName());
		creditCard.setBrandName(registrationForm.getBrandName());
		creditCard.setNumber(registrationForm.getNumber());
		creditCard.setExpirationMonth(registrationForm.getExpirationMonth());
		creditCard.setExpirationYear(registrationForm.getExpirationYear());
		creditCard.setCVV(registrationForm.getCVV());

		customer.setCreditCard(creditCard);

		customer.setName(registrationForm.getName());
		customer.setSurname(registrationForm.getSurname());
		customer.setEmail(registrationForm.getEmail());
		customer.setNationality(registrationForm.getNationality());
		customer.getUserAccount().setUsername(registrationForm.getUsername());
		customer.getUserAccount().setPassword(registrationForm.getPassword());

		return customer;
	}
	
	public void checkPassword(RegistrationForm registrationForm) {
		String password;
		String secondPassword;
		
		password = registrationForm.getPassword();
		secondPassword = registrationForm.getSecondPassword();
		
		if(!password.equals(secondPassword)){
			throw new IllegalArgumentException("passwords dont match");
		}
	}

}
