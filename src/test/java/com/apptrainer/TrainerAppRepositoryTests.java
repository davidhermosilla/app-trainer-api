package com.apptrainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.apptrainer.model.Athlete;
import com.apptrainer.model.PadelTraining;
import com.apptrainer.repository.AthleteRepository;
import com.apptrainer.repository.PadelTrainingRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class TrainerAppRepositoryTests {
	
    @Autowired
    AthleteRepository athelteRepository;
    
    @Autowired
    PadelTrainingRepository padelTrainingRepository;
     
	@Test
	@Order(1)
	void  test1() {
		System.out.println("Create athlete test init");

		Athlete athlete = new Athlete();
		athlete.setFirstName("David");
		athlete.setLastName("Hermosilla");
		athlete.setEmail("davidhermosillagarzon@gamil.com");
		athlete.setAthlete_role("athleta");
		
		Athlete athleteCreated = athelteRepository.save(athlete);
		
		assertEquals(athleteCreated.getId(), 1);
		System.out.println("Create athlete test finish");
	}
	
	@Test
	@Order(2)
	void test2() {
		System.out.println("Create padel training test init");

		PadelTraining padelTraining = new PadelTraining();
		padelTraining.setLevel("Medio");
		padelTraining.setPryce(30);
		padelTraining.setTraining_type("3 Clases semanales");
		
		PadelTraining padelTrainingCreated = padelTrainingRepository.save(padelTraining);
		assertEquals(padelTrainingCreated.getId(), 1);
		System.out.println("Create padel training finish");
	}
	
	@Test
	@Order(3)
	void test3() {
		System.out.println("Update padel training test init");

		PadelTraining padelTraining;
		Optional<PadelTraining> busqueda = padelTrainingRepository.findById(1);
		try {
			padelTraining = busqueda.get();
			padelTraining = getAthleteAndUpdateTraining(padelTraining,1);
			assertEquals(1, padelTraining.getAthletes().size());
		} catch (NoSuchElementException e) {
			assertEquals(true, false);
			System.out.println("Error in c_updatePadelTraining");
		}
		System.out.println("Update padel training finish");
	}

	@Test
	@Order(4)
	void test4() {
		System.out.println("Update padel training test init");

		PadelTraining padelTraining;
		Optional<PadelTraining> busqueda = padelTrainingRepository.findById(1);
		try {
			padelTraining = busqueda.get();
			padelTraining = createAthleteAndUpdateTraining(padelTraining);
			assertEquals(2, padelTraining.getAthletes().size());
		} catch (NoSuchElementException e) {
			assertEquals(true, false);
			System.out.println("Error in c_updatePadelTraining");
		}
		System.out.println("Update padel training finish");
	}	
	
	private PadelTraining getAthleteAndUpdateTraining(PadelTraining padelTraining,int athlete_id) {
		Optional<Athlete> busquedaAthlete = athelteRepository.findById(athlete_id);
		
		busquedaAthlete.ifPresent(athlete -> padelTraining.getAthletes().add(athlete));
		
		PadelTraining padelTrainingCreated = padelTrainingRepository.saveAndFlush(padelTraining);
		
		return padelTrainingCreated;
		
	}	
	
	private PadelTraining createAthleteAndUpdateTraining(PadelTraining padelTraining) {
		Athlete athlete = new Athlete();
		athlete.setFirstName("Juan");
		athlete.setLastName("Casanovas");
		athlete.setEmail("mariquitadeplaya@gmail.com");
		athlete.setAthlete_role("athleta");
		
		padelTraining.getAthletes().add(athlete);
		
		PadelTraining padelTrainingCreated = padelTrainingRepository.saveAndFlush(padelTraining);
		
		return padelTrainingCreated;
		
	}	
	
	
	
}
