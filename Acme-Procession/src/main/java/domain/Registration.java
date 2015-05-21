package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Registration extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Registration() {
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

	private Brother brother;
	private Procession procession;
	private OrdinaryStretch stretch;
	private RegistrationInvoice registrationInvoice;

	@Valid
	@ManyToOne(optional = false)
	public Brother getBrother() {
		return brother;
	}

	public void setBrother(Brother brother) {
		this.brother = brother;
	}

	@Valid
	@ManyToOne(optional = false)
	public Procession getProcession() {
		return procession;
	}

	public void setProcession(Procession procession) {
		this.procession = procession;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public OrdinaryStretch getStretch() {
		return stretch;
	}

	public void setStretch(OrdinaryStretch stretch) {
		this.stretch = stretch;
	}

	@Valid
	@OneToOne(optional=true)
	@Cascade(CascadeType.ALL)
	public RegistrationInvoice getRegistrationInvoice() {
		return registrationInvoice;
	}

	public void setRegistrationInvoice(RegistrationInvoice registrationInvoice) {
		this.registrationInvoice = registrationInvoice;
	}
	
	
	
}
