package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class CostumeReserve extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public CostumeReserve() {
	}

	// Attributes -------------------------------------------------------------
	
	private Date moment;
	private String type;
	
	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	@NotBlank
	@Pattern(regexp = "^purchase$|^rental$")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	// Relationships ----------------------------------------------------------

	private Costume costume;
	private Brother brother;

	@Valid
	@ManyToOne(optional = false)
	public Costume getCostume() {
		return costume;
	}

	public void setCostume(Costume costume) {
		this.costume = costume;
	}

	@Valid
	@ManyToOne(optional = false)
	public Brother getBrother() {
		return brother;
	}

	public void setBrother(Brother brother) {
		this.brother = brother;
	}
	
}
