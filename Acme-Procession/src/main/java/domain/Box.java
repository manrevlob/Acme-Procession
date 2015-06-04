package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Box extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Box() {
	}

	// Attributes -------------------------------------------------------------
	
	private String name;
	private String description;
	private String locality;
	private String location;
	private int numberOfChairs;
	private Money price;
	
	
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
	
	@NotBlank
	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}
	
	@NotBlank
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Min(1)
	public int getNumberOfChairs() {
		return numberOfChairs;
	}

	public void setNumberOfChairs(int numberOfChairs) {
		this.numberOfChairs = numberOfChairs;
	}
	
	@Valid
	@NotNull
	public Money getPrice() {
		return price;
	}

	public void setPrice(Money price) {
		this.price = price;
	}
	
	// Relationships ----------------------------------------------------------

	private Administrator administrator;
	private Collection<BoxInstance> boxInstances;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Administrator getAdministrator() {
		return administrator;
	}

	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
		
	}

	@Valid
	@OneToMany(mappedBy="box")
	public Collection<BoxInstance> getBoxInstances() {
		return boxInstances;
	}

	public void setBoxInstances(Collection<BoxInstance> boxInstances) {
		this.boxInstances = boxInstances;
	}
	
	
	
	
}
