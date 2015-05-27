package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Costume extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Costume() {
	}

	// Attributes -------------------------------------------------------------

	private int size;
	private String status;
	private Money salePrice;
	private Money rentalPrice;
	private String comments;
	private boolean isAvailable;

	@Range(min = 1, max = 100)
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@NotBlank
	@Pattern(regexp = "^new$|^used$|^old$")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Valid
	@NotNull
	@AttributeOverrides({
			@AttributeOverride(name = "amount", column = @Column(name = "salePriceAmount")),
			@AttributeOverride(name = "currency", column = @Column(name = "salePriceCurrency")) })
	public Money getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Money salePrice) {
		this.salePrice = salePrice;
	}

	@Valid
	@NotNull
	@AttributeOverrides({
			@AttributeOverride(name = "amount", column = @Column(name = "rentalPriceAmount")),
			@AttributeOverride(name = "currency", column = @Column(name = "rentalPriceCurrency")) })
	public Money getRentalPrice() {
		return rentalPrice;
	}

	public void setRentalPrice(Money rentalPrice) {
		this.rentalPrice = rentalPrice;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	// Relationships ----------------------------------------------------------

	private Brotherhood brotherhood;
	private Collection<CostumeReserve> costumeReserves;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Brotherhood getBrotherhood() {
		return brotherhood;
	}

	public void setBrotherhood(Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

	@Valid
	@OneToMany(mappedBy = "costume")
	public Collection<CostumeReserve> getCostumeReserves() {
		return costumeReserves;
	}

	public void setCostumeReserves(Collection<CostumeReserve> costumeReserves) {
		this.costumeReserves = costumeReserves;
	}

}
