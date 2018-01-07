package models;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import net.sf.oval.constraint.MaxLength;
import play.data.validation.Email;
import play.data.validation.Equals;
import play.data.validation.IsTrue;
import play.data.validation.MinSize;
import play.data.validation.Range;
import play.data.validation.Required;
import play.db.jpa.Model;
import models.Informations;
@Entity

public class Publications extends Model {

	private Long userId;
	@Required
	private String date;
	@Column(columnDefinition = "LONGTEXT")
	@Required
	private String description;
	private String filePath;
	
	private boolean isDeleted = false;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String file) {
		this.filePath = file;
	}
	
}
