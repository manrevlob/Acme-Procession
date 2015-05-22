package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CostumeRepository;
import domain.Brotherhood;
import domain.Costume;
import forms.CreateCostumesForm;

@Service
@Transactional
public class CostumeService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CostumeRepository costumeRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public CostumeService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Costume findOne(int costumeId) {
		Costume result;

		result = costumeRepository.findOne(costumeId);

		return result;
	}

	public Collection<Costume> findAll() {
		Collection<Costume> result;

		result = costumeRepository.findAll();

		return result;
	}

	public Costume create(Brotherhood brotherhood) {
		Costume costume;

		costume = new Costume();
		costume.setBrotherhood(brotherhood);
		costume.setAvailable(true);

		return costume;
	}

	public Costume save(Costume costume) {
		Costume result;

		Assert.notNull(costume);

		result = costumeRepository.save(costume);

		return result;
	}

	// Other business methods -------------------------------------------------

	public void saveAll(CreateCostumesForm createCostumesForm) {
		int loops;
		Costume costume;
		
		loops = createCostumesForm.getNumberOfCostumes();
		
		Assert.isTrue(loops > 0 && loops <= 15);
		
		for(int i = 0; i < loops; i++) {
			costume = create(createCostumesForm.getBrotherhood());
			costume.setSize(createCostumesForm.getSize());
			costume.setStatus(createCostumesForm.getStatus());
			costume.setSalePrice(createCostumesForm.getSalePrice());
			costume.setRentalPrice(createCostumesForm.getRentalPrice());
			costume.setComments(createCostumesForm.getComments());
			
			save(costume);
		}
	}

}
