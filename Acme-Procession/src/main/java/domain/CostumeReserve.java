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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class CostumeReserve extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public CostumeReserve() {
	}

	// Attributes -------------------------------------------------------------
	
	private Date moment;
	
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
