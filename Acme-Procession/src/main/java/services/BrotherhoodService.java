package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BrotherhoodRepository;
import security.Authority;
import security.UserAccount;
import domain.Brother;
import domain.Brotherhood;
import forms.AddBigBrotherForm;

@Service
@Transactional
public class BrotherhoodService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BrotherhoodRepository brotherhoodRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;

	@Autowired
	private BrotherService brotherService;

	// Constructors -----------------------------------------------------------

	public BrotherhoodService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Brotherhood findOne(int brotherhoodId) {
		Brotherhood result;

		result = brotherhoodRepository.findOne(brotherhoodId);

		return result;
	}

	public Collection<Brotherhood> findAll() {
		Collection<Brotherhood> result;

		result = brotherhoodRepository.findAll();

		return result;
	}

	public Brotherhood create() {
		Brotherhood result;
		Collection<Brother> collectionWithcreator;

		Assert.isTrue(actorService.isBrother());
		Assert.isTrue(brotherService.findByPrincipal().getIsAuthorized());

		result = new Brotherhood();

		collectionWithcreator = new ArrayList<Brother>();
		collectionWithcreator.add(brotherService.findByPrincipal());

		result.setBrothers(collectionWithcreator);
		result.setBigBrothers(collectionWithcreator);

		return result;
	}

	public Brotherhood save(Brotherhood brotherhood) {
		Brotherhood result;
		int actualYear;

		Assert.notNull(brotherhood);
		Assert.isTrue(brotherhood.getBigBrothers().contains(
				brotherService.findByPrincipal()));

		actualYear = Calendar.getInstance().get(Calendar.YEAR);

		Assert.isTrue(brotherhood.getFoundationYear() <= actualYear,
				"brotherhood.invalidYear.error");

		result = brotherhoodRepository.save(brotherhood);

		if (brotherhood.getId() == 0) {
			UserAccount updatedUserAccount;
			Authority bigBrotherAuthority;
			Collection<Authority> authorities;
			Brother brother;

			brother = brotherService.findByPrincipal();

			updatedUserAccount = brother.getUserAccount();
			authorities = updatedUserAccount.getAuthorities();

			bigBrotherAuthority = new Authority();
			bigBrotherAuthority.setAuthority(Authority.BIGBROTHER);

			if (!authorities.contains(bigBrotherAuthority)) {
				updatedUserAccount.addAuthority(bigBrotherAuthority);
				brother.setUserAccount(updatedUserAccount);
			}

			updateCreatorBrotherhoods(result);
		}

		return result;
	}

	// Other business methods -------------------------------------------------

	public Brotherhood findOneIfPrincipal(int brotherhoodId) {
		Brotherhood result;

		result = brotherhoodRepository.findOne(brotherhoodId);

		Assert.isTrue(result.getBigBrothers().contains(
				brotherService.findByPrincipal()));

		return result;
	}

	public Collection<Brotherhood> findMines() {
		Collection<Brotherhood> result;

		Assert.isTrue(actorService.isBrother());

		result = brotherhoodRepository.findMines(brotherService
				.findByPrincipal());

		return result;
	}

	public Collection<Brotherhood> findOwns() {
		Collection<Brotherhood> result;

		Assert.isTrue(actorService.isBrother());
		Assert.isTrue(brotherService.findByPrincipal().getOwnBrotherhoods() != null
				&& brotherService.findByPrincipal().getOwnBrotherhoods().size() > 0);

		result = brotherhoodRepository.findOwns(brotherService
				.findByPrincipal());

		return result;
	}

	public void updateCreatorBrotherhoods(Brotherhood brotherhood) {
		Brother brother;
		Collection<Brotherhood> newMyBrotherhoods;
		Collection<Brotherhood> newOwnBrotherhoods;

		brother = brotherService.findByPrincipal();

		newMyBrotherhoods = brother.getBrotherhoods();
		newOwnBrotherhoods = brother.getOwnBrotherhoods();

		newMyBrotherhoods.add(brotherhood);
		newOwnBrotherhoods.add(brotherhood);

		brother.setBrotherhoods(newMyBrotherhoods);
		brother.setOwnBrotherhoods(newOwnBrotherhoods);

		brotherService.save(brother);
	}

	public void addBrother(AddBigBrotherForm addBrotherForm) {
		Brotherhood brotherhood;
		Brother brother;
		UserAccount updatedUserAccount;
		Authority bigBrotherAuthority;
		Collection<Authority> authorities;
		Collection<Brotherhood> newOwnBrotherhoods;
		Collection<Brother> newBigBrothers;

		Assert.notNull(addBrotherForm);
		Assert.isTrue(addBrotherForm.getBrotherhood().getBigBrothers()
				.contains(brotherService.findByPrincipal()));
		Assert.isTrue(addBrotherForm.getBrotherhood().getBrothers()
				.contains(addBrotherForm.getBrother()));

		brotherhood = addBrotherForm.getBrotherhood();
		brother = addBrotherForm.getBrother();

		updatedUserAccount = brother.getUserAccount();
		authorities = updatedUserAccount.getAuthorities();

		bigBrotherAuthority = new Authority();
		bigBrotherAuthority.setAuthority(Authority.BIGBROTHER);

		if (!authorities.contains(bigBrotherAuthority)) {
			updatedUserAccount.addAuthority(bigBrotherAuthority);
			brother.setUserAccount(updatedUserAccount);
		}

		newOwnBrotherhoods = brother.getOwnBrotherhoods();
		newOwnBrotherhoods.add(brotherhood);

		newBigBrothers = brotherhood.getBigBrothers();
		newBigBrothers.add(brother);

		brotherhood.setBigBrothers(newBigBrothers);
		brother.setOwnBrotherhoods(newOwnBrotherhoods);

		brotherService.save(brother);
		save(brotherhood);
	}

	public Brotherhood findByImage(int logoId) {
		Brotherhood result;

		Assert.isTrue(actorService.isBrother());

		result = brotherhoodRepository.findByImage(logoId);

		return result;
	}

	// Dashboard
	public Collection<Brotherhood> findAllOrderByNumReg() {
		Collection<Brotherhood> result;

		Assert.isTrue(actorService.isAdministrator());

		result = brotherhoodRepository.findAllOrderByNumReg();

		return result;
	}

	public Collection<Object[]> findAllByAssess() {
		Collection<Object[]> result;

		Assert.isTrue(actorService.isAdministrator());

		result = brotherhoodRepository.findAllByAssess();

		return result;
	}

}
