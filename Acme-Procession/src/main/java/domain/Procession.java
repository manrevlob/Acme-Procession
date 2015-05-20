package domain;

import java.util.Calendar;
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

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Procession extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Procession() {
	}

	// Attributes -------------------------------------------------------------

	private String name;
	private Date startMoment;
	private Date endMoment;
	private String locality;
	private String itinerary;
	private Money associatedCost;
	private String comments;
	private boolean isClosedManually;

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartMoment() {
		return startMoment;
	}

	public void setStartMoment(Date startMoment) {
		this.startMoment = startMoment;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndMoment() {
		return endMoment;
	}

	public void setEndMoment(Date endMoment) {
		this.endMoment = endMoment;
	}

	@NotBlank
	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	@NotBlank
	public String getItinerary() {
		return itinerary;
	}

	public void setItinerary(String itinerary) {
		this.itinerary = itinerary;
	}

	@Valid
	@NotNull
	public Money getAssociatedCost() {
		return associatedCost;
	}

	public void setAssociatedCost(Money associatedCost) {
		this.associatedCost = associatedCost;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public boolean getIsClosedManually() {
		return isClosedManually;
	}

	public void setIsClosedManually(boolean isClosedManually) {
		this.isClosedManually = isClosedManually;
	}

	// Derived attributes -----------------------------------------------------
	@Transient
	public Integer getNumberOfRegistrations() {
		return getRegistrations().size();
	}
	
	@Transient
	public boolean getIsClosed() {
		boolean oneWeekBeforeArrived;
		Calendar actualDateLessOneWeek;
		
		actualDateLessOneWeek = Calendar.getInstance();
		actualDateLessOneWeek.add(Calendar.WEEK_OF_YEAR, -1);
		
		oneWeekBeforeArrived = getStartMoment().before(actualDateLessOneWeek.getTime());
		
		return getIsClosedManually() || oneWeekBeforeArrived;
	}
	
	@Transient
	public boolean getIsClosedByTime() {
		boolean oneWeekBeforeArrived;
		Calendar actualDateLessOneWeek;
		
		actualDateLessOneWeek = Calendar.getInstance();
		actualDateLessOneWeek.add(Calendar.WEEK_OF_YEAR, -1);
		
		oneWeekBeforeArrived = getStartMoment().before(actualDateLessOneWeek.getTime());
		
		return oneWeekBeforeArrived;
	}

	// Relationships ----------------------------------------------------------

	private Brotherhood brotherhood;
	private Collection<StretchOrder> stretchOrders;
	private Collection<Registration> registrations;
	private Collection<Assessment> assessments;

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
	@NotNull
	@OneToMany(mappedBy = "procession")
	public Collection<StretchOrder> getStretchOrders() {
		return stretchOrders;
	}

	public void setStretchOrders(Collection<StretchOrder> stretchOrders) {
		this.stretchOrders = stretchOrders;
	}

	@Valid
	@OneToMany(mappedBy = "procession")
	public Collection<Registration> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(Collection<Registration> registrations) {
		this.registrations = registrations;
	}

	@Valid
	@OneToMany(mappedBy = "procession")
	public Collection<Assessment> getAssessments() {
		return assessments;
	}
	public void setAssessments(Collection<Assessment> assessments) {
		this.assessments = assessments;
	}

}
