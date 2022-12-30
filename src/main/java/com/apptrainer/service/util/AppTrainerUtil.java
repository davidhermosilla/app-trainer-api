package com.apptrainer.service.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.apptrainer.exception.AppTrainerException;
import com.apptrainer.model.Athlete;
import com.apptrainer.model.Group;
import com.apptrainer.model.PadelTraining;
import com.apptrainer.model.Training;
import com.apptrainer.repository.AthleteRepository;
import com.apptrainer.repository.GroupRepository;
import com.apptrainer.repository.TrainingRepository;

public class AppTrainerUtil {
	/**
	 * @param athlete
	 * @return 
	 * @throws AppTrainerException
	 */
	public static Athlete checkAthlete(AthleteRepository athleteRepository, int athlete_id) throws AppTrainerException {
		Optional<Athlete> athletedbexist = athleteRepository.findById(athlete_id);
		
		//If the athleta does not exists error because should be created previously
		if (!athletedbexist.isPresent()) {
			throw new AppTrainerException("Error: El atleta con id: "+ athlete_id+" no existe");
		}
		
		return athletedbexist.get();
	}
	
	/**
	 * @param athletes
	 * @param padelTraining
	 */
	public static void removeAthletes(List<Athlete> athletes, PadelTraining padelTraining) {
		List<Athlete> athletesdb = new ArrayList<Athlete>(padelTraining.getAthletes());
		for (Athlete athlete: athletesdb) {
    		
    		boolean encontrado = false;
    		for (Athlete athleterequest: athletes) {
    			if (athleterequest.equals(athlete)) {
    				encontrado=true;
    				break;
    			}
    		}
    		
    		if (!encontrado) {
    			padelTraining.getAthletes().remove(athlete);
    		}
    		
    	}
	}
	
	/**
	 * @param athletes
	 * @param padelTraining
	 * @throws AppTrainerException
	 */
	public static void addAthletes(AthleteRepository athleteRepository,List<Athlete> athletes, PadelTraining padelTraining) throws AppTrainerException {
		for (Athlete athlete: athletes) {
			AppTrainerUtil.checkAthlete(athleteRepository,athlete.getId());
    		
    		boolean encontrado = false;
    		for (Athlete athletedb: padelTraining.getAthletes()) {
    			if (athletedb.equals(athlete)) {
    				encontrado=true;
    				break;
    			}
    		}
    		
    		if (!encontrado) {
    			padelTraining.getAthletes().add(athlete);
    		}
    		
    	}
	}
	
	/**
	 * @param id
	 * @param group
	 * @return
	 * @throws AppTrainerException
	 */
	public static Group checkGroup(GroupRepository groupRepository,Integer id) throws AppTrainerException {
		Group group=null;
		try {
			group = groupRepository.findById(id).get();
		} catch(NoSuchElementException ex) {
			throw new AppTrainerException("El grupo "+id+" no existe");
		}
		return group;
	}
	
	/**
	 * @param id
	 * @param group
	 * @return
	 * @throws AppTrainerException
	 */
	public static Training checkTraining(TrainingRepository trainingRepository,Integer id) throws AppTrainerException {
		Training training=null;
		try {
			training = trainingRepository.findById(id).get();
		} catch(NoSuchElementException ex) {
			throw new AppTrainerException("El entrenamiento "+id+" no existe");
		}
		return training;
	}
	
	public static LocalDateTime convertToLocalDateViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDateTime();
	}
	
	public static Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
	    return java.sql.Timestamp.valueOf(dateToConvert);
	}
	
	/**
	 * @param training_date
	 * @return
	 */
	public static Date addMonth(Date training_date) {	
		
//		GregorianCalendar calendar = new GregorianCalendar();
//	    calendar.setTime(training_date);
//		
//	    calendar.add((GregorianCalendar.MONTH), 1);
	    
		LocalDateTime ldt = AppTrainerUtil.convertToLocalDateViaInstant(training_date);
//		LocalDateTime ldt_next = ldt.with(TemporalAdjusters.firstDayOfNextMonth());
		LocalDateTime ldt_next = ldt.plusMonths(1);
		Date newdate = AppTrainerUtil.convertToDateViaSqlTimestamp(ldt_next);
	    
//	    Date newdate = java.util.Date.from(calendar.toZonedDateTime().toInstant());
		return newdate;
	}
	
}
