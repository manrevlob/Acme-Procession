package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class MessageFolder extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public MessageFolder() {

	}

	// Attributes -------------------------------------------------------------

	private String name;

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Relationships ----------------------------------------------------------

	private Collection<Message> messages;

	@Valid
	@OneToMany(mappedBy = "messageFolder")
	public Collection<Message> getMessages() {
		return messages;
	}

	public void setMessages(Collection<Message> messages) {
		this.messages = messages;
	}

	// toString ---------------------------------------------------------------

	@Override
	public String toString() {
		return "MessageFolder [name=" + name + "]";
	}

}
