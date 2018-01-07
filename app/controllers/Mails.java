package controllers;

import models.Mail;
import models.Profile;
import org.apache.commons.mail.*; 
import play.*;
import play.mvc.*;
import java.util.*;

public class Mails extends Mailer {
	public static void welcome(Profile user) {
		setCharset("UTF-8");
		setSubject("Witaj %s w systemie eProf", user.getFirstname());
		setFrom("eProf <eprof.formularz@gmail.com>");
		addRecipient(user.getEmail());
		send(user);
	}
	public static void sendEmail(Mail mail) {
		setCharset("UTF-8");
		setSubject("E-prof wiadomość od: "+ mail.getName());
		setFrom("eProf <eprof.formularz@gmail.com>");
		addRecipient(mail.getRecipient());
		send(mail);
	}

}