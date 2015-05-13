package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import domain.Brother;
import domain.Brotherhood;

@Access(AccessType.PROPERTY)
public class AddBigBrotherForm {

	// Constructors ---------------------------------------------------

	public AddBigBrotherForm() {
		super();
	}

	// Attributes -----------------------------------------------------

	private Brotherhood brotherhood;
	private Brother brother;

	// Methods ---------------------------------------------------------

	@NotNull
	@Valid
	public Brotherhood getBrotherhood() {
		return brotherhood;
	}

	public void setBrotherhood(Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

	@NotNull
	@Valid
	public Brother getBrother() {
		return brother;
	}

	public void setBrother(Brother brother) {
		this.brother = brother;
	}

}
