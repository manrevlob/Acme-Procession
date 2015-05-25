package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Entity
@Access(AccessType.PROPERTY)
public class Image extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Image() {
	}

	// Attributes -------------------------------------------------------------
	private String photo;
	private CommonsMultipartFile filedata;

	@Lob
	@Column(columnDefinition = "TEXT")
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Transient
	public CommonsMultipartFile getFiledata() {
		return filedata;
	}

	public void setFiledata(CommonsMultipartFile filedata) {
		this.filedata = filedata;
	}

	// Relationships ----------------------------------------------------------

}
