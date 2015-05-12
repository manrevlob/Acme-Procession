package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Brother extends Customer {
	
	// Constructors -----------------------------------------------------------

	public Brother() {
	}

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	Collection<Brotherhood> brotherhoods;
	Collection<Brotherhood> ownBrotherhoods;
	Collection<Registration> registrations;

	@Valid
	@ManyToMany(mappedBy = "brothers")
	public Collection<Brotherhood> getBrotherhoods() {
		return brotherhoods;
	}

	public void setBrotherhoods(Collection<Brotherhood> brotherhoods) {
		this.brotherhoods = brotherhoods;
	}

	@Valid
	@ManyToMany(mappedBy = "bigBrothers")
	public Collection<Brotherhood> getOwnBrotherhoods() {
		return ownBrotherhoods;
	}

	public void setOwnBrotherhoods(Collection<Brotherhood> ownBrotherhoods) {
		this.ownBrotherhoods = ownBrotherhoods;
	}

	@Valid
	@OneToMany(mappedBy = "brother")
	public Collection<Registration> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(Collection<Registration> registrations) {
		this.registrations = registrations;
	}

}
