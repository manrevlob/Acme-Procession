package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import domain.Brotherhood;

@Access(AccessType.PROPERTY)
public class BrotherhoodAndStretchTypeSelectForm {

	// Constructors ---------------------------------------------------

	public BrotherhoodAndStretchTypeSelectForm() {
		super();
	}

	// Attributes -----------------------------------------------------

	private Brotherhood brotherhood;
	private String type;

	@Valid
	@NotNull
	public Brotherhood getBrotherhood() {
		return brotherhood;
	}

	public void setBrotherhood(Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

	@NotNull
	@Pattern(regexp = "^ordinary$|^float$")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
