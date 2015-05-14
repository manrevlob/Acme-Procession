package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Administrator extends Actor {

	// Constructors -----------------------------------------------------------

	public Administrator() {
	}

	// Attributes -------------------------------------------------------------
	
	// Relationships ----------------------------------------------------------
	
	private Collection<Request> requests;
	private Collection<Box> boxes;
	
	@Valid
	@OneToMany(mappedBy="administrator")
	public Collection<Request> getRequests() {
		return requests;
	}
	public void setRequests(Collection<Request> requests) {
		this.requests = requests;
	}
	
	@Valid
	@OneToMany(mappedBy="administrator")
	public Collection<Box> getBoxes() {
		return boxes;
	}

	public void setBoxes(Collection<Box> boxes) {
		this.boxes = boxes;
	}
	
}
