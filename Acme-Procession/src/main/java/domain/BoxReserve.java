package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames="reserveCode")})
public class BoxReserve extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public BoxReserve() {
	}

	// Attributes -------------------------------------------------------------
	
	private Date createMoment;
	private String reserveCode;
	private Integer numberOfChair;
	private Date date;
	private Money totalCost;
	private boolean isCancelled;
	

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCreateMoment() {
		return createMoment;
	}

	public void setCreateMoment(Date createMoment) {
		this.createMoment = createMoment;
	}

	@NotBlank
	public String getReserveCode() {
		return reserveCode;
	}

	public void setReserveCode(String reserveCode) {
		this.reserveCode = reserveCode;
	}

	@Min(1)
	public Integer getNumberOfChair() {
		return numberOfChair;
	}

	public void setNumberOfChair(Integer numberOfChair) {
		this.numberOfChair = numberOfChair;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Valid
	@NotNull
	public Money getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Money totalCost) {
		this.totalCost = totalCost;
	}
	
	public boolean getIsCancelled() {
		return isCancelled;
	}

	public void setIsCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
	
	// Relationships ----------------------------------------------------------

	private BoxInstance boxInstance;
	private Viewer viewer;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public BoxInstance getBoxInstance() {
		return boxInstance;
	}

	public void setBoxInstance(BoxInstance boxInstance) {
		this.boxInstance = boxInstance;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Viewer getViewer() {
		return viewer;
	}

	public void setViewer(Viewer viewer) {
		this.viewer = viewer;
	}
	
	
	
}
