package com.apptrainer.service.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.apptrainer.exception.AppTrainerException;
import com.apptrainer.model.Athlete;
import com.apptrainer.model.Exercise;
import com.apptrainer.model.Group;
import com.apptrainer.model.Training;
import com.apptrainer.repository.AthleteRepository;
import com.apptrainer.repository.ExerciseRepository;
import com.apptrainer.repository.GroupRepository;
import com.apptrainer.repository.TrainingRepository;

public class AppTrainerUtil {
	
	/**
	 * @param athlete
	 * @return 
	 * @throws AppTrainerException
	 */
	public static Athlete checkAthlete(AthleteRepository athleteRepository, int athlete_id,MessageSource mensajes) throws AppTrainerException {
		Optional<Athlete> athletedbexist = athleteRepository.findById(athlete_id);
		
		//If the athleta does not exists error because should be created previously
		if (!athletedbexist.isPresent()) {
			throw new AppTrainerException(AppTrainerUtil.getString(mensajes,"AppTrainerUtil.athlete")+ athlete_id+AppTrainerUtil.getString(mensajes,"AppTrainerUtil.notexists")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		return athletedbexist.get();
	}
	
	public static Exercise checkExercise(ExerciseRepository exerciseRepository, Integer exercise_id,
			MessageSource mensajes) throws AppTrainerException {
		Optional<Exercise> exerciseDb = exerciseRepository.findById(exercise_id);
		
		//If the athleta does not exists error because should be created previously
		if (!exerciseDb.isPresent()) {
			throw new AppTrainerException(AppTrainerUtil.getString(mensajes,"AppTrainerUtil.exercise")+ exercise_id+AppTrainerUtil.getString(mensajes,"AppTrainerUtil.notexists")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		return exerciseDb.get();

	}	
	
	/**
	 * @param athletes
	 * @param padelTraining
	 */
	public static void removeAthletes(List<Athlete> athletes, Training training) {
		List<Athlete> athletesdb = new ArrayList<Athlete>(training.getAthletes());
		for (Athlete athlete: athletesdb) {
    		
    		boolean encontrado = false;
    		for (Athlete athleterequest: athletes) {
    			if (athleterequest.equals(athlete)) {
    				encontrado=true;
    				break;
    			}
    		}
    		
    		if (!encontrado) {
    			training.getAthletes().remove(athlete);
    		}
    		
    	}
	}
	
	/**
	 * @param athletes
	 * @param padelTraining
	 * @throws AppTrainerException
	 */
	public static void addAthletes(AthleteRepository athleteRepository,List<Athlete> athletes, Training training,MessageSource mensajes) throws AppTrainerException {
		for (Athlete athlete: athletes) {
			AppTrainerUtil.checkAthlete(athleteRepository,athlete.getId(),mensajes);
    		
    		boolean encontrado = false;
    		for (Athlete athletedb: training.getAthletes()) {
    			if (athletedb.equals(athlete)) {
    				encontrado=true;
    				break;
    			}
    		}
    		
    		if (!encontrado) {
    			training.getAthletes().add(athlete);
    		}
    		
    	}
	}
	
	/**
	 * @param id
	 * @param group
	 * @return
	 * @throws AppTrainerException
	 */
	public static Group checkGroup(GroupRepository groupRepository,Integer id,MessageSource mensajes) throws AppTrainerException {
		Group group=null;
		try {
			group = groupRepository.findById(id).get();
		} catch(NoSuchElementException ex) {
			throw new AppTrainerException(AppTrainerUtil.getString(mensajes,"AppTrainerUtil.group")+id+AppTrainerUtil.getString(mensajes,"AppTrainerUtil.notexists")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return group;
	}
	
	/**
	 * @param id
	 * @param group
	 * @return
	 * @throws AppTrainerException
	 */
	public static Training checkTraining(TrainingRepository trainingRepository,Integer id,MessageSource mensajes) throws AppTrainerException {
		Training training=null;
		try {
			training = trainingRepository.findById(id).get();
		} catch(NoSuchElementException ex) {
			throw new AppTrainerException(AppTrainerUtil.getString(mensajes,"AppTrainerUtil.training")+id+AppTrainerUtil.getString(mensajes,"AppTrainerUtil.notexists")); //$NON-NLS-1$ //$NON-NLS-2$
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

	public static String getString(MessageSource mensajes,String key) {
		return mensajes.getMessage(key, null, LocaleContextHolder.getLocale());
	}

}
