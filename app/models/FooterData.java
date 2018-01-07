package models;

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

@Entity

public class FooterData extends Model {

	private Long userId;
	private String phoneNumber;
	private String email;
	private String location;
	private String roomNumber;
	private String consultationHours;
	private String webPage;
	private String position;
	private String pathToPhoto = "/public/images/none.jpg";
	
	public String getPathToPhoto() {
		return pathToPhoto;
	}
	
	public void setPathToPhoto(String pathToPhoto) {
		this.pathToPhoto = pathToPhoto;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getWebPage() {
		return webPage;
	}
	public void setWebPage(String webPage) {
		this.webPage = webPage;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public String getConsultationHours() {
		return consultationHours;
	}
	public void setConsultationHours(String consultationHours) {
		this.consultationHours = consultationHours;
	}
	public Profile getProfileById(Long id) {
		return Profile.findById(id);
	}
	
}
