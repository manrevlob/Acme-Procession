package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Message() {

	}

	// Attributes -------------------------------------------------------------

	private String subject;
	private String body;
	private Date moment;
	private String attachment;

	@NotBlank
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@NotBlank
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	@URL
	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	// Relationships ----------------------------------------------------------

	private Actor sender;
	private Actor recipient;
	private MessageFolder messageFolder;

	@Valid
	@ManyToOne(optional = false)
	public Actor getSender() {
		return sender;
	}

	public void setSender(Actor sender) {
		this.sender = sender;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getRecipient() {
		return recipient;
	}

	public void setRecipient(Actor recipient) {
		this.recipient = recipient;
	}

	@Valid
	@ManyToOne(optional = false)
	public MessageFolder getMessageFolder() {
		return messageFolder;
	}

	public void setMessageFolder(MessageFolder messageFolder) {
		this.messageFolder = messageFolder;
	}

	// toString ---------------------------------------------------------------

	@Override
	public String toString() {
		return "Message [sender=" + sender + ", recipient=" + recipient
				+ ", moment=" + moment + ", subject=" + subject + ", body="
				+ body + "]";
	}

}
