package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import security.LoginService;

@Entity
@Access(AccessType.PROPERTY)
public class Brotherhood extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Brotherhood() {
	}

	// Attributes -------------------------------------------------------------

	private String name;
	private int foundationYear;
	private String history;

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Min(1500)
	public int getFoundationYear() {
		return foundationYear;
	}

	public void setFoundationYear(int foundationYear) {
		this.foundationYear = foundationYear;
	}

	@NotBlank
	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	// Derived attributes -----------------------------------------------------
	@Transient
	public Integer getNumberOfBrothers() {
		return getBrothers().size();
	}

	@Transient
	public boolean getUserIsOwner() {
		boolean result;
		int principalId;

		result = false;

		if (LoginService.getPrincipal() != null) {
			principalId = LoginService.getPrincipal().getId();

			for (Brother o : getBigBrothers()) {
				if (o.getUserAccount().getId() == principalId) {
					result = true;
					break;
				}
			}
		}

		return result;
	}

	@Transient
	public boolean getUserIsRegiter(){
		boolean result;
		int principalId;
		
		result = false;
		
		if(LoginService.getPrincipal() != null) {
			principalId = LoginService.getPrincipal().getId();
			
			for(Brother o : getBrothers()) {
				if(o.getUserAccount().getId() == principalId) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	// Relationships ----------------------------------------------------------

	private Collection<Brother> brothers;
	private Collection<Brother> bigBrothers;
	private Collection<Procession> processions;
	private Collection<Stretch> stretches;
	private Collection<Carving> carvings;
	private Collection<Costume> costumes;

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "brotherhoods")
	public Collection<Brother> getBrothers() {
		return brothers;
	}

	public void setBrothers(Collection<Brother> brothers) {
		this.brothers = brothers;
	}

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "ownBrotherhoods")
	public Collection<Brother> getBigBrothers() {
		return bigBrothers;
	}

	public void setBigBrothers(Collection<Brother> bigBrothers) {
		this.bigBrothers = bigBrothers;
	}

	@Valid
	@OneToMany(mappedBy = "brotherhood")
	public Collection<Procession> getProcessions() {
		return processions;
	}

	public void setProcessions(Collection<Procession> processions) {
		this.processions = processions;
	}

	@Valid
	@OneToMany(mappedBy = "brotherhood")
	public Collection<Stretch> getStretches() {
		return stretches;
	}

	public void setStretches(Collection<Stretch> stretches) {
		this.stretches = stretches;
	}

	@Valid
	@OneToMany(mappedBy = "brotherhood")
	public Collection<Carving> getCarvings() {
		return carvings;
	}

	public void setCarvings(Collection<Carving> carvings) {
		this.carvings = carvings;
	}

	@Valid
	@OneToMany(mappedBy = "brotherhood")
	public Collection<Costume> getCostumes() {
		return costumes;
	}

	public void setCostumes(Collection<Costume> costumes) {
		this.costumes = costumes;
	}

}
