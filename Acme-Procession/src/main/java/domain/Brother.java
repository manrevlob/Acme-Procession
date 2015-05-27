package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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

	private boolean isAuthorized;

	public boolean getIsAuthorized() {
		return isAuthorized;
	}

	public void setIsAuthorized(boolean isAuthorized) {
		this.isAuthorized = isAuthorized;
	}

	// Relationships ----------------------------------------------------------

	private Collection<Brotherhood> brotherhoods;
	private Collection<Brotherhood> ownBrotherhoods;
	private Collection<Registration> registrations;
	private Collection<CostumeReserve> costumeReserves;
	private Collection<Request> requests;

	@Valid
	@ManyToMany
	public Collection<Brotherhood> getBrotherhoods() {
		return brotherhoods;
	}

	public void setBrotherhoods(Collection<Brotherhood> brotherhoods) {
		this.brotherhoods = brotherhoods;
	}

	@Valid
	@ManyToMany
	@JoinTable(name = "bigBrother_ownBrotherhood", joinColumns = @JoinColumn(name = "bigBrothers_id"), inverseJoinColumns = @JoinColumn(name = "ownBrotherhoods_id"))
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
	
	@Valid
	@OneToMany(mappedBy = "brother")
	public Collection<CostumeReserve> getCostumeReserves() {
		return costumeReserves;
	}

	public void setCostumeReserves(Collection<CostumeReserve> costumeReserves) {
		this.costumeReserves = costumeReserves;
	}

	@Valid
	@OneToMany(mappedBy = "brother")
	public Collection<Request> getRequests() {
		return requests;
	}

	public void setRequests(Collection<Request> requests) {
		this.requests = requests;
	}

}
