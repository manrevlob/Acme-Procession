package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Viewer extends Customer {
	
	// Constructors -----------------------------------------------------------

	public Viewer() {
	}

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------
	private Collection<Assessment> assessments;

	@Valid
	@OneToMany(mappedBy = "viewer")
	public Collection<Assessment> getAssessments() {
		return assessments;
	}
	public void setAssessments(Collection<Assessment> assessments) {
		this.assessments = assessments;
	}
	
	
}
