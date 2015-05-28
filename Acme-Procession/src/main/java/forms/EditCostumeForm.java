package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import domain.Costume;

@Access(AccessType.PROPERTY)
public class EditCostumeForm {

	// Constructors ---------------------------------------------------

	public EditCostumeForm() {
	}

	// Attributes -----------------------------------------------------

	private Costume costume;
	private boolean noToSale;
	private boolean noToRental;

	@Valid
	@NotNull
	public Costume getCostume() {
		return costume;
	}

	public void setCostume(Costume costume) {
		this.costume = costume;
	}
	
	public boolean getNoToSale() {
		return noToSale;
	}

	public void setNoToSale(boolean noToSale) {
		this.noToSale = noToSale;
	}

	public boolean getNoToRental() {
		return noToRental;
	}

	public void setNoToRental(boolean noToRental) {
		this.noToRental = noToRental;
	}

}
