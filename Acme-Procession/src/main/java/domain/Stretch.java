package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Stretch extends DomainEntity {
	
	// Constructors -----------------------------------------------------------

	public Stretch() {
	}

	// Attributes -------------------------------------------------------------

	private String name;
	private String description;

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// Relationships ----------------------------------------------------------

	private Procession procession;

	@Valid
	@ManyToOne(optional = false)
	public Procession getProcession() {
		return procession;
	}

	public void setProcession(Procession procession) {
		this.procession = procession;
	}

}
