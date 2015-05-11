package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Viewer extends Customer {
	
	// Constructors -----------------------------------------------------------

	public Viewer() {
	}

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------
	
}
