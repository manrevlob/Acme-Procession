package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import domain.Brotherhood;
import domain.Image;

@Access(AccessType.PROPERTY)
public class AddImageToBrotherhoodForm {

	// Constructors ---------------------------------------------------

	public AddImageToBrotherhoodForm() {
		super();
	}

	// Attributes -----------------------------------------------------

	private Brotherhood brotherhood;
	private Image image;

	@Valid
	@NotNull
	public Brotherhood getBrotherhood() {
		return brotherhood;
	}

	public void setBrotherhood(Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

	@Valid
	@NotNull
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
