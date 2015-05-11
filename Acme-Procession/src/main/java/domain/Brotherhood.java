package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Brotherhood extends DomainEntity {
	
	// Constructors -----------------------------------------------------------

	public Brotherhood() {
	}

	// Attributes -------------------------------------------------------------

	private String name;
	private Date foundationYear;
	private String history;

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getFoundationYear() {
		return foundationYear;
	}

	public void setFoundationYear(Date foundationYear) {
		this.foundationYear = foundationYear;
	}

	@NotBlank
	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	// Relationships ----------------------------------------------------------

	private Collection<Brother> brothers;
	private Collection<Brother> bigBrothers;
	private Collection<Procession> processions;
	private Collection<Carving> carvings;

	@Valid
	@NotNull
	@ManyToMany
	public Collection<Brother> getBrothers() {
		return brothers;
	}

	public void setBrothers(Collection<Brother> brothers) {
		this.brothers = brothers;
	}

	@Valid
	@NotNull
	@ManyToMany
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
	public Collection<Carving> getCarvings() {
		return carvings;
	}

	public void setCarvings(Collection<Carving> carvings) {
		this.carvings = carvings;
	}

}
