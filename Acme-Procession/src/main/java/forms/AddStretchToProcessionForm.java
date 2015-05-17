package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import domain.Procession;
import domain.Stretch;

@Access(AccessType.PROPERTY)
public class AddStretchToProcessionForm {

	// Constructors ---------------------------------------------------

	public AddStretchToProcessionForm() {
		super();
	}

	// Attributes -----------------------------------------------------

	private Procession procession;
	private Stretch stretch;
	
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
	public Stretch getStretch() {
		return stretch;
	}
	public void setStretch(Stretch stretch) {
		this.stretch = stretch;
	}

}
