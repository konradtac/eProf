package models;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.sun.xml.internal.ws.wsdl.writer.document.xsd.Import;

import net.sf.oval.constraint.MaxLength;
import play.data.validation.Email;
import play.data.validation.Equals;
import play.data.validation.IsTrue;
import play.data.validation.MinSize;
import play.data.validation.Range;
import play.data.validation.Required;
import play.db.jpa.Model;
import sun.security.jca.GetInstance.Instance;
import models.Profile;


@Entity

public class Informations extends Model {
	private Long userId;
	@Required
	private String subject;
	@Column(columnDefinition = "LONGTEXT")
	@Required
	private String content;
	private boolean isDeleted = false;
	private String date;

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Profile getProfileById(Long id) {
		return Profile.findById(id);
	}


	
}
