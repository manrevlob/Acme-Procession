package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import domain.Image;
import domain.Procession;

@Access(AccessType.PROPERTY)
public class AddImageToProcessionForm {

	// Constructors ---------------------------------------------------

	public AddImageToProcessionForm() {
		super();
	}

	// Attributes -----------------------------------------------------

	private Procession procession;
	private Image image;

	@Valid
	@NotNull
	public Procession getProcession() {
		return procession;
	}

	public void setProcession(Procession procession) {
		this.procession = procession;
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
