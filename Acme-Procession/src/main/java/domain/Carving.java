package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Carving extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Carving() {
	}

	// Attributes -------------------------------------------------------------

	private String name;
	private String description;
	private String author;
	private int year;
	private String comments;

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotBlank
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Min(0)
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	// Relationships ----------------------------------------------------------

	private Brotherhood brotherhood;
	private Collection<FloatStretch> associatedStretchs;

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
	@ManyToMany(mappedBy = "carvings")
	public Collection<FloatStretch> getAssociatedStretchs() {
		return associatedStretchs;
	}

	public void setAssociatedStretchs(
			Collection<FloatStretch> associatedStretchs) {
		this.associatedStretchs = associatedStretchs;
	}

}
