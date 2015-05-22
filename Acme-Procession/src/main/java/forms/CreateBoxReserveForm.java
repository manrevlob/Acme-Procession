package forms;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import domain.Box;

@Access(AccessType.PROPERTY)
public class CreateBoxReserveForm {

	// Constructors ---------------------------------------------------

	public CreateBoxReserveForm() {
		super();
	}

	// Attributes -----------------------------------------------------

	private Date date;
	private Collection<Integer> numbersOfChairs;
	private Box box;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@ElementCollection
	public Collection<Integer> getNumbersOfChairs() {
		return numbersOfChairs;
	}
	public void setNumbersOfChairs(Collection<Integer> numbersOfChairs) {
		this.numbersOfChairs = numbersOfChairs;
	}
	
	
	public Box getBox() {
		return box;
	}
	public void setBox(Box box) {
		this.box = box;
	}

	// Methods ---------------------------------------------------------

	

	

}
