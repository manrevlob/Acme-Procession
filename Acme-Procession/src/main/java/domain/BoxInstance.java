package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class BoxInstance extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public BoxInstance() {
	}

	// Attributes -------------------------------------------------------------
	
	// Relationships ----------------------------------------------------------
	
}
