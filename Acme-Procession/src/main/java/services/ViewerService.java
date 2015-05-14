package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ViewerRepository;
import security.LoginService;
import security.UserAccount;
import security.Authority;
import utilities.PasswordCode;
import domain.Brother;
import domain.MessageFolder;
import domain.Viewer;

@Service
@Transactional
public class ViewerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ViewerRepository viewerRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private MessageFolderService messageFolderService;

	// Constructors -----------------------------------------------------------

	public ViewerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	public Viewer create() {
		Viewer result;
		
		result = new Viewer();
		
		result.setUserAccount(createUserAccount());
		result.setMessageFolders(createMessageFolders());
		
		return result;
	}
	
	public void save(Viewer viewer) {
		
		Assert.notNull(viewer);
		
		if(viewer.getId()==0){
			String passwordCoded;
			
			passwordCoded = PasswordCode.encode(viewer.getUserAccount().getPassword());
			viewer.getUserAccount().setPassword(passwordCoded);
		}
		
		viewerRepository.save(viewer);
	}

	public Viewer findOne(int viewerId) {
		Viewer result;

		result = viewerRepository.findOne(viewerId);

		return result;
	}

	public Collection<Viewer> findAll() {
		Collection<Viewer> result;

		result = viewerRepository.findAll();

		return result;
	}

	// Other business methods -------------------------------------------------
	
	public UserAccount createUserAccount() {
		UserAccount result;
		Collection<Authority> authorities;
		Authority authority;
		
		authority = new Authority();
		authority.setAuthority("VIEWER");
		
		authorities = new ArrayList<Authority>();
		authorities.add(authority);
		
		result = new UserAccount();
		result.setAuthorities(authorities);
		
		return result;
	}
	
	public Collection<MessageFolder> createMessageFolders() {
		Collection<MessageFolder> result;
		MessageFolder inbox;
		MessageFolder outbox;
		MessageFolder trashbox;
		
		inbox = new MessageFolder();
		outbox = new MessageFolder();
		trashbox = new MessageFolder();
		
		result = new ArrayList<MessageFolder>();
		result.add(inbox);
		result.add(outbox);
		result.add(trashbox);
		
		return result;
	}
	
	public void registerToTheSystem(Viewer viewer) {
		Integer year;
	 	Integer month;

	 	year = Calendar.getInstance().get(Calendar.YEAR);
	 	month = Calendar.getInstance().get(Calendar.MONTH);
		
	 	Assert.isTrue(viewer.getCreditCard().getExpirationYear()>= year);
	 	
	 	if(viewer.getCreditCard().getExpirationYear().equals(year)){
	 		Assert.isTrue(viewer.getCreditCard().getExpirationMonth() > month, "credit card expired");
	 	}
	 	
	 	viewer.setMessageFolders(saveSystemFolders(viewer));
	 	 	
	 	save(viewer);
	}
	
	public Collection<MessageFolder> saveSystemFolders(Viewer viewer) {
		List<MessageFolder> result;
		List<MessageFolder> aux;
		MessageFolder inbox;
		MessageFolder outbox;
		MessageFolder trashbox;
		
		aux = (List<MessageFolder>)viewer.getMessageFolders();
		inbox = aux.get(0);
		outbox = aux.get(1);
		trashbox = aux.get(2);
		
		
		inbox.setName("Inbox");
		outbox.setName("Outbox");
		trashbox.setName("Trashbox");
		
		inbox = messageFolderService.save(inbox);
		outbox = messageFolderService.save(outbox);
		trashbox = messageFolderService.save(trashbox);
		
		result = new ArrayList<MessageFolder>();
		result.add(inbox);
		result.add(outbox);
		result.add(trashbox);
		
		return result;
	}

	public Viewer findByPrincipal() {
		Viewer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		result = viewerRepository.findByPrincipal(userAccount.getId());

		Assert.notNull(result);

		return result;
	}

}
