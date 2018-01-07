package controllers;
import play.*;
import play.data.validation.EmailCheck;
import play.data.validation.Valid;
import play.data.validation.Validation.ValidationResult;
import play.libs.Mail;
import play.mvc.*;
import play.mvc.Scope.Params;

import java.util.*;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

import groovy.ui.Console;
import models.*;

public class Application extends Controller {

	public static void index() {
		List<Profile> findUsers = Profile.findAll();
		render(findUsers);
	}
	
	public void profile(Long id) {
		System.out.println("PROFILE"+id);
		Profile profile = Profile.findById(id);
		render("@profile",profile);
	}

	public static void login() {
		render();
	}

	public static void registerUser(@Valid Profile user) throws EmailException {
		
		validation.equals(user.getPassword(), user.getPasswordConfirm());
		user.setGenerateKey(generateKey());	
		if(validation.hasErrors()) {
			render("@login");
		}
		user.save();
		Mails.welcome(user);
		FooterData fd = new FooterData();
		fd.setUserId(user.getId());
		fd.save();
		session.put("@login", user.getEmail());
		render("@welcome");
		
	}
	
	public static void loginUser(@Valid Profile user) {
		if (validation.error(user.getEmail()) == null && validation.error(user.getPassword()) == null) {
			if(checkIfExistUser(user)) {
				System.out.println("Użytkownik istnieje");
				session.put("@login", user.getEmail());
				
				AdminPanel.dashboard();
			}
		}
		render("@login");
		
		
	 }
	
	public static void authorization(String key) {
		System.out.println(key);
		Profile findUser = Profile.find("byGenerateKey", key).
				first();
		if(findUser!=null && key.equals(findUser.getGenerateKey())){
			
			AdminPanel.dashboard();
			
		}
		
	}

	private static boolean checkIfExistUser(Profile user) {
		Profile findUser = Profile.find("byEmailAndPassword", user.getEmail(), user.getPassword()).
				first();
		return findUser != null && user.getPassword().equals(findUser.getPassword()) && user.getEmail().equals(findUser.getEmail());
	}
	private static String generateKey() {
		return UUID.randomUUID().toString();
	}
	
	 
	

}

