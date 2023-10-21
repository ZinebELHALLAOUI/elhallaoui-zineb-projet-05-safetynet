package app.infrastructure.repository;

import app.domain.model.FireStation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class FireStationJsonRepositoryTest {

    private final String jsonData = "{\"persons\": [{\"firstName\": \"John\", \"lastName\": \"Boyd\", \"address\": \"1509 Culver St\", \"city\": \"Culver\", \"zip\": \"97451\", \"phone\": \"841-874-6512\", \"email\": \"jaboyd@email.com\"}, {\"firstName\": \"Jacob\", \"lastName\": \"Boyd\", \"address\": \"1509 Culver St\", \"city\": \"Culver\", \"zip\": \"97451\", \"phone\": \"841-874-6513\", \"email\": \"drk@email.com\"}], \"firestations\": [{\"address\": \"1509 Culver St\", \"station\": \"3\"}], \"medicalrecords\": [{\"firstName\": \"John\", \"lastName\": \"Boyd\", \"birthdate\": \"03/06/1984\", \"medications\": [\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\": [\"nillacilan\"]}, {\"firstName\": \"Jacob\", \"lastName\": \"Boyd\", \"birthdate\": \"03/06/1989\", \"medications\": [\"pharmacol:5000mg\", \"terazine:10mg\", \"noznazol:250mg\"], \"allergies\": []}]}";
    private File jsonFilePath;

    private FireStationJsonRepository fireStationJsonRepository;

    @BeforeEach
    public void beforeEach() {
        jsonFilePath = new File("src/test/resources/tmptFile.json");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFilePath))) {
            writer.write(jsonData);
        } catch (IOException e) {
        }
        fireStationJsonRepository = new FireStationJsonRepository(jsonFilePath);
    }

    @AfterEach
    public void afterEach() {
        jsonFilePath.delete();
    }

    @Test
    public void should_find_fire_station_by_address() {
        //given : jsonData

        //when
        Optional<FireStation> result = fireStationJsonRepository.findByAddress("1509 Culver St");

        //then
        assertThat(result).isPresent();

        //when
        result = fireStationJsonRepository.findByAddress("unknown");

        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void should_find_fire_station_by_station_number() {
        //given : jsonData

        //when
        Set<FireStation> result = fireStationJsonRepository.findByStationNumber(3);

        //then
        assertThat(result).isNotEmpty();

        //when
        result = fireStationJsonRepository.findByStationNumber(404);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void should_add_fire_station() {
        //given : jsonData
        FireStation fireStation = new FireStation(10, "test address");

        //when
        fireStationJsonRepository.add(fireStation);

        //then
        assertThat(fireStationJsonRepository.findByStationNumber(10)).isNotEmpty();
    }

    @Test
    public void should_update_fire_station() {
        //given : jsonData
        FireStation fireStation = new FireStation(10, "1509 Culver St");

        //when
        fireStationJsonRepository.update(fireStation);

        //then
        assertThat(fireStationJsonRepository.findByStationNumber(10)).isNotEmpty();
    }

    @Test
    public void should_delete_fire_station_by_address() {
        //given : jsonData

        //when
        fireStationJsonRepository.deleteByAddress("1509 Culver St");

        //then
        assertThat(fireStationJsonRepository.findByAddress("1509 Culver St")).isEmpty();
    }

    @Test
    public void should_delete_fire_station_by_station_number() {
        //given : jsonData

        //when
        fireStationJsonRepository.deleteByStationNumber(3);

        //then
        assertThat(fireStationJsonRepository.findByStationNumber(3)).isEmpty();
    }

}