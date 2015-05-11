package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Customer;

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

}
