package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class OrdinaryStretch extends Stretch {
	
	// Constructors -----------------------------------------------------------

	public OrdinaryStretch() {
	}

	// Attributes -------------------------------------------------------------

	private int maxNumberOfBrothers;

	@Min(1)
	public int getMaxNumberOfBrothers() {
		return maxNumberOfBrothers;
	}

	public void setMaxNumberOfBrothers(int maxNumberOfBrothers) {
		this.maxNumberOfBrothers = maxNumberOfBrothers;
	}

	// Relationships ----------------------------------------------------------

	private Collection<Registration> registrations;

	@Valid
	@OneToMany(mappedBy = "stretch")
	public Collection<Registration> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(Collection<Registration> registrations) {
		this.registrations = registrations;
	}

}
