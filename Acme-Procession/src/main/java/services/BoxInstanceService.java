package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxInstanceRepository;
import domain.Box;
import domain.BoxInstance;
import domain.BoxReserve;

@Service
@Transactional
public class BoxInstanceService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BoxInstanceRepository boxInstanceRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;
	@Autowired
	private BoxService boxService;
	@Autowired
	private AdministratorService administratorService;

	// Constructors -----------------------------------------------------------

	public BoxInstanceService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public BoxInstance create(int boxId) {
		BoxInstance result;
		Box box;
		int availableChairs;

		Assert.isTrue(actorService.isAdministrator());

		box = boxService.findOne(boxId);
		availableChairs = box.getNumberOfChairs();

		result = new BoxInstance();
		result.setBox(box);
		result.setAvailableChairs(availableChairs);

		return result;
	}

	public BoxInstance save(BoxInstance boxInstance) {
		BoxInstance result;
		Date actual;

		Assert.notNull(boxInstance);
		Assert.isTrue(actorService.isViewer()
				|| (actorService.isAdministrator() && boxInstance.getBox()
						.getAdministrator() == administratorService
						.findByPrincipal()));

		actual = new Date();

		Assert.isTrue(boxInstance.getDate().after(actual), "date invalid");

		result = boxInstanceRepository.save(boxInstance);

		return result;
	}

	public BoxInstance findOne(int boxInstanceId) {
		BoxInstance result;

		Assert.isTrue(actorService.isAdministrator());

		result = boxInstanceRepository.findOne(boxInstanceId);

		return result;
	}

	public Collection<BoxInstance> findAll() {
		Collection<BoxInstance> result;

		Assert.isTrue(actorService.isAdministrator());

		result = boxInstanceRepository.findAll();

		return result;
	}

	// Other business methods -------------------------------------------------

	public void isEditable(BoxInstance boxInstance) {
		Collection<BoxReserve> boxReserves;

		Assert.isTrue(actorService.isAdministrator());

		boxReserves = boxInstance.getBoxReserves();

		Assert.isTrue(boxReserves.isEmpty(), "cant edit");

	}

	public Collection<BoxInstance> findByBox(int boxId) {
		Collection<BoxInstance> result;

		Assert.isTrue(actorService.isAdministrator());

		result = boxInstanceRepository.findByBox(boxId);

		return result;
	}

	public Collection<BoxInstance> findAvailablesByBox(int boxId) {
		Collection<BoxInstance> result;

		Assert.isTrue(actorService.isAdministrator() || actorService.isViewer());

		result = boxInstanceRepository.findAvailablesByBox(boxId);

		return result;
	}

	public void saveBoxInstance(BoxInstance boxInstance) {
		Box box;
		BoxInstance result;
		Collection<BoxInstance> boxInstances;

		Assert.notNull(boxInstance);

		box = boxInstance.getBox();

		if (boxInstance.getId() == 0) {

			result = save(boxInstance);

			boxInstances = box.getBoxInstances();
			boxInstances.add(result);
			box.setBoxInstances(boxInstances);

			boxService.save(box);
		} else {
			result = save(boxInstance);
		}

	}

}
