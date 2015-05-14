package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Stretch extends DomainEntity {

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

	private Collection<StretchOrder> stretchOrders;

	@Valid
	@OneToMany(mappedBy = "stretch")
	public Collection<StretchOrder> getStretchOrders() {
		return stretchOrders;
	}

	public void setStretchOrders(Collection<StretchOrder> stretchOrders) {
		this.stretchOrders = stretchOrders;
	}

}
