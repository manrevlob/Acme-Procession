package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
public class Money {
	
	// Constructors -----------------------------------------------------------

	public Money() {

	}

	// Attributes -------------------------------------------------------------

	private double amount;
	private String currency;

	@Min(0)
	@Digits(integer = 9, fraction = 2)
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@NotBlank
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	// Relationships ----------------------------------------------------------

	// toString ---------------------------------------------------------------
	@Override
	public String toString() {
		return amount + " " + currency;
	}
}
