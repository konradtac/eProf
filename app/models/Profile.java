package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.mysql.fabric.xmlrpc.base.Array;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import controllers.AdminPanel;
import play.data.validation.Email;
import play.data.validation.Equals;
import play.data.validation.IsTrue;
import play.data.validation.MinSize;
import play.data.validation.Range;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity

public class Profile extends Model {

	@Required(message = "podaj imię")
	private String firstname;
	@Required(message = "podaj nazwisko")
	private String lastname;
	@Required(message = "podaj hasło")
	private String password;
	@Required(message = "powtórz hasło")
	private String passwordConfirm;
	@Required(message = "podaj e-mail")
	@Email(message = "zły format adresu email")
	private String email;
	@Required(message = "Podaj tytuł naukowy")
	private String title;
	private String generateKey;

	public List<Informations> getInformationsList() {
		return AdminPanel.findAllInformationsByUserId(this.getId());
	}

	public List<Publications> getPublicationsList() {
		return AdminPanel.findAllPublicationsByUserId(this.getId());
	}

	public Publications getPublications() {
		return Publications.find("byUserId", this.getId()).first();
	}

	public Map<Integer, List<String>> getCvsMap() {
		return AdminPanel.formatToView(this.getCv());
	}

	public Cv getCv() {
		return Cv.find("byUserIdAndIsDeleted", this.getId(), false).first();
	}

	public Biography getBiography() {
		return Biography.find("byUserIdAndIsDeleted", this.getId(), false).first();
	}

	public FooterData getFooterData() {
		return FooterData.find("byUserId", this.getId()).first();
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenerateKey() {
		return generateKey;
	}

	public void setGenerateKey(String generateKey) {
		this.generateKey = generateKey;
	}
}
