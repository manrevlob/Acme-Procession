package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class BoxInstance extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public BoxInstance() {
	}

	// Attributes -------------------------------------------------------------
	
	private Date date;
	private int availableChairs;
	
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
		
	public int getAvailableChairs() {
		return availableChairs;
	}

	public void setAvailableChairs(int availableChairs) {
		this.availableChairs = availableChairs;
	}
	
	// Derived attributes -----------------------------------------------------
	
		@Transient
		public String getDateAndChairs() {
			return getDate()+ "," + " " + getAvailableChairs();
		}
	
	// Relationships ----------------------------------------------------------

	private Box box;
	private Collection<BoxReserve> boxReserves;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Box getBox() {
		return box;
	}

	public void setBox(Box box) {
		this.box = box;
	}


	@Valid
	@OneToMany(mappedBy = "boxInstance")
	public Collection<BoxReserve> getBoxReserves() {
		return boxReserves;
	}

	public void setBoxReserves(Collection<BoxReserve> boxReserves) {
		this.boxReserves = boxReserves;
	}
	
	
	
	
	
}
