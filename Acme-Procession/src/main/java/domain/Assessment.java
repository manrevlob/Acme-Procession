package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Assessment extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Assessment() {
	}

	// Attributes -------------------------------------------------------------
	
	private Integer valoration;
	private String street;
	
	@NotNull
	@Range(min = 1, max = 12)
	public Integer getValoration() {
		return valoration;
	}
	public void setValoration(Integer valoration) {
		this.valoration = valoration;
	}
	
	@NotBlank
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	// Relationships ----------------------------------------------------------

	private Viewer viewer;
	private Procession procession;
	
	@Valid
	@ManyToOne(optional = false)
	public Viewer getViewer() {
		return viewer;
	}
	public void setViewer(Viewer viewer) {
		this.viewer = viewer;
	}
	
	@Valid
	@ManyToOne(optional = false)
	public Procession getProcession() {
		return procession;
	}
	public void setProcession(Procession procession) {
		this.procession = procession;
	}
	
	
	
}
