package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import domain.Request;

@Access(AccessType.PROPERTY)
public class AddCommentToRequestForm {
		
	// Constructors ---------------------------------------------------
	
	public AddCommentToRequestForm() {
		super();
	}
	
	// Attributes -----------------------------------------------------
	
	private String comment;
	private Request request;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Valid
	@NotNull
	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}
}
