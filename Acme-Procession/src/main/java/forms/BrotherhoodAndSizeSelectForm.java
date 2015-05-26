package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import domain.Brotherhood;

@Access(AccessType.PROPERTY)
public class BrotherhoodAndSizeSelectForm {

	// Constructors ---------------------------------------------------

	public BrotherhoodAndSizeSelectForm() {
		super();
	}

	// Attributes -----------------------------------------------------

	private Brotherhood brotherhood;
	private int minSize;
	private int maxSize;

	@Valid
	@NotNull
	public Brotherhood getBrotherhood() {
		return brotherhood;
	}

	public void setBrotherhood(Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

	@Range(min = 1, max = 100)
	public int getMinSize() {
		return minSize;
	}

	public void setMinSize(int minSize) {
		this.minSize = minSize;
	}

	@Range(min = 1, max = 100)
	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

}
