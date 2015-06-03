package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.persistence.Index;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "orderNumber")})
public class StretchOrder extends DomainEntity {
	
	// Constructors -----------------------------------------------------------

	public StretchOrder() {
	}

	// Attributes -------------------------------------------------------------

	private int orderNumber;

	@Min(1)
	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	// Relationships ----------------------------------------------------------

	private Procession procession;
	private Stretch stretch;

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
	public Stretch getStretch() {
		return stretch;
	}

	public void setStretch(Stretch stretch) {
		this.stretch = stretch;
	}
	
}
