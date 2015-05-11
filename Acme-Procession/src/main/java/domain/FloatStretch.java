package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class FloatStretch extends Stretch {
	
	// Constructors -----------------------------------------------------------

	public FloatStretch() {
	}

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------
	
	private Collection<Carving> carvings;

	@Valid
	@NotNull
	@ManyToMany
	public Collection<Carving> getCarvings() {
		return carvings;
	}

	public void setCarvings(Collection<Carving> carvings) {
		this.carvings = carvings;
	}
	
}
