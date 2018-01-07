package controllers;
import play.*;
import play.data.validation.Valid;
import play.db.jpa.JPABase;
import play.mvc.Controller;
import sun.security.jca.GetInstance.Instance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mysql.fabric.xmlrpc.base.Array;

import groovy.lang.DelegatesTo;
import models.*;

public class AdminPanel extends Controller {
	
	public void sendEmail(Mail mail, Long id) {
		mail.setRecipient(findUserByLogin().getEmail());
		Mails.sendEmail(mail);
	}
	
	public void dashboard(FooterData arg) {
		FooterData createFooterData = arg;
		if(arg.getId() == null) {
			createFooterData = findFooterDataByUserId();
		}
			
			render("@dashboard",createFooterData);
		
	}
	public static void dashboard() {
		
		FooterData createFooterData = findFooterDataByUserId();
		
		render("@dashboard", createFooterData);
		
	}

	public void informations(Informations arg) {
		if(arg.getId() == null) {
			arg = findInformationByUserId();
		}
		if(arg!=null) {
			List<Informations> informationsToSend = findAllInformationsByUserId(arg.getUserId());
			render("@informations",informationsToSend);
		}
		render("@informations",arg);
	}
	public void biography(Biography arg) {
		if(arg.getId() == null) {
			arg = findBiographyByUserId();
		}
		render("@biography",arg);
	}
	
	public void cv(Cv arg) {
		if(arg.getId() == null) {
			arg = findCvByUserId();
		}
		if(arg!=null) {
			Map<Integer, List<String>> mapToSend = formatToView(arg);
			render("@cv",mapToSend);
		}
		render("@cv",arg);
	}
	
	public void publications(Publications arg) {
		if(arg.getId() == null) {
			arg = findPublicationByUserId();
		}
		if(arg!=null) {
List<Publications> publicationsToSend = findAllPublicationsByUserId(arg.getUserId());
			render("@publications",publicationsToSend);
		}
		render("@publications",arg);
	}
	
	public static List<Publications> findAllPublicationsByUserId(Long uId) {
		List<Publications> publicationsToSend = Publications.findAll();
		List<Publications> publicationsToSendWuthUserId = new ArrayList();
		
		for (Publications publications : publicationsToSend) {
			if(publications.getUserId() == uId) {
				publicationsToSendWuthUserId.add(publications);
			}
		}
		return publicationsToSendWuthUserId;
	}
	
	public static List<Informations> findAllInformationsByUserId(Long uId) {
		List<Informations> informationsToSend = Informations.findAll();
		List<Informations> informationToSendWithUserId = new ArrayList();
		
		for (Informations informations : informationsToSend) {
			if(informations.getUserId() == uId && informations.isDeleted() == false) {
				informationToSendWithUserId.add(informations);
			}
		}
		return informationToSendWithUserId;
	}
	
	
	public void savePublication(@Valid Publications data, File file) throws IOException {
		Profile findUser = findUserByLogin();
		String pathToSave = Play.applicationPath+"/files/"+file.getName(); 
		updateIsDeletedStatusForPublications(findUser);
		copyFile(file, new File(pathToSave));
		Publications publication = new Publications();
		publication.setDate(data.getDate());
		publication.setDescription(data.getDescription());
		publication.setFilePath(pathToSave);
		publication.setUserId(findUser.getId());
		publication.save();
	}
	
	public void saveFooterDatas(String data, String type) {
		System.out.println(data);
		System.out.println(type);
		Profile findUser = findUserByLogin();
		FooterData createFooterData = new FooterData();
		FooterData findFooterData = findFooterDataByUserId();
		if (findFooterData != null) createFooterData = findFooterData;
		
		switch(type) {
		case "phone": createFooterData.setPhoneNumber(data);break;
		case "roomNumber": createFooterData.setRoomNumber(data);break;
		case "location": createFooterData.setLocation(data);break;
		case "consultationHours": createFooterData.setConsultationHours(data);break;
		case "webPage": createFooterData.setWebPage(data);break;
		case "position": createFooterData.setPosition(data);break;
		
		}
		createFooterData.setUserId(findUser.getId());
		createFooterData.save();
		dashboard(createFooterData);
	}

	public void savePhoto(File photo) throws IOException {

		String pathToSave = Play.applicationPath+"/public/images/"+photo.getName(); 
		copyFile(photo, new File(pathToSave));
		FooterData footerData = findFooterDataByUserId();
		footerData.setPathToPhoto("/public/images/" + photo.getName());
		footerData.save();
		dashboard();
		
	}
	private static void copyFile(File source, File dest) throws IOException {
	    Files.copy(source.toPath(), dest.toPath());
	}
	
	public File download(Long id) {
		Publications findPublication = findPublicationById(id);
		String filePath = findPublication.getFilePath();
		return new File(filePath);
	}
	
	public Publications findPublicationById(Long id) {
		return Publications.findById(id);
	}
	
	public static Map<Integer, List<String>> formatToView(Cv arg) {
		Map <Integer, List<String>> cvMap = new HashMap<Integer, List<String>>();
		cvMap.put(0, Arrays.asList(arg.getDateFor().split(",")));
		cvMap.put(1, Arrays.asList(arg.getDateTo().split(",")));
		cvMap.put(2, Arrays.asList(arg.getDescription().split(",")));
		
		Map <Integer, List<String>> mapToSend = new HashMap<Integer, List<String>>();
		for (Integer i : cvMap.keySet()) {
			int k=0;
			for(String j : cvMap.get(i)) {
					 List<String> itemsList = mapToSend.get(k);

					    // if list does not exist create it
					    if(itemsList == null) {
					         itemsList = new ArrayList<String>();
					         itemsList.add(j);
					        
					    }else {
					    	itemsList.add(j);
					    }

					mapToSend.put(k, itemsList);
				
				k++;
			}
					
		}
		
		return mapToSend;
	}

	
	public void saveBiography(String data) {
		Profile findUser = findUserByLogin();
		updateIsDeletedStatusForBiography(findUser);
		Biography biography = new Biography();
		biography.setUserId(findUser.getId());
		biography.setContent(data);
		biography.save();
		biography(biography);

	}

	public void saveCv(@Valid Cv data){
		Profile findUser = findUserByLogin();
		updateIsDeletedStatusForCv(findUser);
		Cv curriculumVitae = new Cv();
		curriculumVitae.setUserId(findUser.getId());
		curriculumVitae.setDateFor(data.getDateFor());
		curriculumVitae.setDateTo(data.getDateTo());
		curriculumVitae.setDescription(data.getDescription());
		curriculumVitae.save();
		cv(data);
		
	}
	
	public void saveInformation(@Valid Informations data) {
		Profile findUser = findUserByLogin();
		Informations information = new Informations();
		information.setContent(data.getContent());
		information.setSubject(data.getSubject());
		information.setUserId(findUser.getId());
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		information.setDate(dateFormat.format(new Date()));
		information.save();
		informations(data);
		
	}
	
	public void deleteInformation(Long id){

		Informations arg = Informations.findById(id);
		Informations.delete("id", id);
		informations(arg);
		
	
	}
	private void updateIsDeletedStatusForBiography(Profile findUser) {
		Biography biography = findBiographyByUserId();
		if(biography != null) {
			biography.setDeleted(true);
			biography.save();
		}
	}
	private void updateIsDeletedStatusForCv(Profile findUser) {
		Cv cv = findCvByUserId();
		if(cv != null) {
			cv.setDeleted(true);
			cv.save();
		}
	}
	private void updateIsDeletedStatusForPublications(Profile findUser) {
		Publications publication = findPublicationByUserId();
		if(publication != null) {
			publication.setDeleted(true);
			publication.save();
		}
	}
	public void logout() {
		session.clear();
		render("@../Application/login");
	}
	
	public static Profile findUserByLogin() {
		System.out.println(session.get("@login"));
		return Profile.find("byEmail", session.get("@login")).first();
	}
	public Biography findBiographyByUserId() {
		return Biography.find("byUserIdAndIsDeleted", findUserByLogin().getId(), false).first();
	}
	public Cv findCvByUserId() {
		return Cv.find("byUserIdAndIsDeleted", findUserByLogin().getId(), false).first();
	}
	public Publications findPublicationByUserId() {
		return Publications.find("byUserIdAndIsDeleted", findUserByLogin().getId(), false).first();
	}
	public Informations findInformationByUserId() {
		return Informations.find("byUserIdAndIsDeleted", findUserByLogin().getId(), false).first();
	}
	public static FooterData findFooterDataByUserId() {
		
		Long id = findUserByLogin().getId();
		System.out.println("ID:"+id);
		return FooterData.find("byUserId", id).first();
		
		
	}
	

	
	
	
	
}

