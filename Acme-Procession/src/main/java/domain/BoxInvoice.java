package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class BoxInvoice extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public BoxInvoice() {
	}

	// Attributes -------------------------------------------------------------
	
	// Relationships ----------------------------------------------------------
	
}
