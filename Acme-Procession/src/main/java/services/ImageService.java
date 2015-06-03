package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ImageRepository;
import domain.Brotherhood;
import domain.Image;

@Service
@Transactional
public class ImageService {
	// Managed repository -----------------------------------------------------

	@Autowired
	private ImageRepository imageRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;
	@Autowired
	private BrotherhoodService brotherhoodService;

	// Constructors -----------------------------------------------------------

	public ImageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Image save(Image image) {
		Image result;
		Brotherhood brotherhood;
		byte[] base64;

		brotherhood = brotherhoodService.findByImage(image.getId());

		if (brotherhood != null) {
			delete(image);
		}

		Assert.notNull(image);
		Assert.isTrue(actorService.isBrother());

		byte[] imgBase64 = image.getFiledata().getBytes();

		base64 = Base64.encode(imgBase64);

		image.setPhoto(new String(base64));
		result = imageRepository.save(image);

		return result;
	}

	public void delete(Image image) {
		imageRepository.delete(image);
	}

	// Other business methods -------------------------------------------------

	public void checkImage(Image image) {
		Assert.notNull(image);
		Assert.isTrue(actorService.isBrother());

		String contentType = image.getFiledata().getContentType().toString();
		Assert.isTrue(contentType.contains("image/"));
		Assert.isTrue(image.getFiledata().getSize() != 0, "commit.image.error");

	}

}
