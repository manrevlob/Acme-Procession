package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BrotherhoodRepository;
import domain.Brother;
import domain.Brotherhood;

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
		
		actualYear = Calendar.getInstance().get(Calendar.YEAR);
		
		Assert.isTrue(brotherhood.getFoundationYear() <= actualYear, "brotherhood.invalidYear.error");

		result = brotherhoodRepository.save(brotherhood);

		if (brotherhood.getId() == 0) {
			updateCreatorBrotherhoods(result);
		}

		return result;
	}

	// Other business methods -------------------------------------------------

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
		// TODO comprobar que tenga permisos

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
}
