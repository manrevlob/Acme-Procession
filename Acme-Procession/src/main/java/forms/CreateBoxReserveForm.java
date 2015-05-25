package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import domain.Box;
import domain.BoxInstance;

@Access(AccessType.PROPERTY)
public class CreateBoxReserveForm {

	// Constructors ---------------------------------------------------

	public CreateBoxReserveForm() {
		super();
	}

	// Attributes -----------------------------------------------------

	private Box box;
	private BoxInstance boxInstance;
	private int chairs;
	
	
	@Min(1)	
	public Integer getChairs() {
		return chairs;
	}

	public void setChairs(int chairs) {
		this.chairs = chairs;
	}
	
	@Valid
	@NotNull
	public BoxInstance getBoxInstance() {
		return boxInstance;
	}
	
	public void setBoxInstance(BoxInstance boxInstance) {
		this.boxInstance = boxInstance;
	}
	
	public Box getBox() {
		return box;
	}
	public void setBox(Box box) {
		this.box = box;
	}

	// Methods ---------------------------------------------------------

	

	

}
