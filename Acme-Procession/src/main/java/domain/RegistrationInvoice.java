package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class RegistrationInvoice extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public RegistrationInvoice() {
	}

	// Attributes -------------------------------------------------------------
	
	// Relationships ----------------------------------------------------------
	
}
