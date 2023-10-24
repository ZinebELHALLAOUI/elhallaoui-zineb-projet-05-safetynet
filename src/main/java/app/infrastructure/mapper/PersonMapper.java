package app.infrastructure.mapper;

import app.domain.model.MedicalRecord;
import app.domain.model.Person;
import app.infrastructure.controller.dto.request.PersonAddRequest;
import app.infrastructure.controller.dto.response.MedicalRecordResponse;
import app.infrastructure.controller.dto.response.*;
import app.infrastructure.entity.MedicalRecordEntity;
import app.infrastructure.entity.PersonEntity;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class PersonMapper {
    public static PersonEntity PersonToPersonEntity(final Person person) {
        if (person == null) return null;
        PersonEntity entity = new PersonEntity();
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setCity(person.getCity());
        entity.setPhone(person.getPhone());
        entity.setZip(person.getZip());
        entity.setEmail(person.getEmail());
        return entity;
    }

    public static Person requestToPerson(final PersonAddRequest personAddRequest) {
        if (personAddRequest == null) return null;
        final Person person = new Person(
                personAddRequest.getFirstName(),
                personAddRequest.getLastName(),
                personAddRequest.getAddress(),
                personAddRequest.getCity(),
                personAddRequest.getZip(),
                personAddRequest.getPhone(),
                personAddRequest.getEmail()
        );
        return person;
    }

    public static PersonDto personToDto(final Person person) {
        if (person == null) return null;
        final PersonDto response = new PersonDto();
        response.setFirstName(person.getFirstName());
        response.setLastName(person.getLastName());
        response.setAddress(person.getAddress());
        response.setCity(person.getCity());
        response.setPhone(person.getPhone());
        response.setZip(person.getZip());
        response.setEmail(person.getEmail());
        return response;
    }

    public static MedicalRecordResponse personToMedicalRecordResponse(final Person person) {
        if (person == null) return null;
        final MedicalRecordResponse response = new MedicalRecordResponse();
        response.setFirstName(person.getFirstName());
        response.setLastName(person.getLastName());
        response.setBirthdate(person.getBirthdate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        response.setMedications(new ArrayList<>(person.getMedicalRecord().getMedications()));
        response.setAllergies(new ArrayList<>(person.getMedicalRecord().getAllergies()));
        return response;
    }

    public static MedicalRecordEntity getMedicalRecordOfPerson(final Person person) {
        if (person == null) return null;
        final MedicalRecordEntity entity = new MedicalRecordEntity();
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setBirthdate(person.getBirthdate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        entity.setMedications(new ArrayList<>(person.getMedicalRecord().getMedications()));
        entity.setAllergies(new ArrayList<>(person.getMedicalRecord().getAllergies()));
        return entity;
    }

    public static MedicalRecord entityToMedicalRecord(final MedicalRecordEntity entity) {
        if (entity == null) return null;
        final MedicalRecord medicalRecord = new MedicalRecord(entity.getFirstName(), entity.getLastName(), entity.getMedications(), entity.getAllergies());
        return medicalRecord;
    }


    public static PersonsByFireStationResponse personsToPersonsByFireStationNumber(Set<Person> persons) {
        PersonsByFireStationResponse personsByFireStationResponse = new PersonsByFireStationResponse();

        List<PersonByFireStation> personsMapping = persons.stream()
                .filter(person -> person.getBirthdate() != null)
                .map(person -> {
                    PersonByFireStation personByFireStation = new PersonByFireStation();
                    personByFireStation.setFirstName(person.getFirstName());
                    personByFireStation.setLastName(person.getLastName());
                    personByFireStation.setPhone(person.getPhone());
                    personByFireStation.setAddress(person.getAddress());
                    return personByFireStation;
                }).toList();
        personsByFireStationResponse.setPersons(personsMapping);
        personsByFireStationResponse.setNumberOfAdults(persons.stream().filter(person -> person.isMajor()).count());
        personsByFireStationResponse.setNumberOfMinors(persons.stream().filter(person -> person.isMinor()).count());

        return personsByFireStationResponse;
    }

    public static ChildAlertResponse personsToChildrenAlert(Set<Person> persons) {
        ChildAlertResponse childAlertResponse = new ChildAlertResponse();
        List<ChildAlert> childAlerts = persons
                .stream()
                .filter(person -> person.getBirthdate() != null)
                .filter(person -> person.isMinor())
                .map(person -> {
                    ChildAlert childAlert = new ChildAlert();
                    childAlert.setFirstName(person.getFirstName());
                    childAlert.setLastName(person.getLastName());
                    childAlert.setAge(person.getAge());
                    childAlert.setMembers(
                            persons
                                    .stream()
                                    .filter(p -> !p.getId().equals(person.getId()))
                                    .map(member -> PersonMapper.personToDto(member))
                                    .toList()
                    );
                    return childAlert;
                }).toList();
        childAlertResponse.setChildrenAlert(childAlerts);
        return childAlertResponse;
    }

    public static PersonsWithFireStationAndMedicalRecordResponse personsToPersonsWithFireStationAndMedicalRecordResponse(Set<Person> persons) {
        PersonsWithFireStationAndMedicalRecordResponse response = new PersonsWithFireStationAndMedicalRecordResponse();
        List<PersonWithFireStationAndMedicalRecord> personWithFireStationAndMedicalRecords = persons.stream()
                .filter(person -> person.getBirthdate() != null)
                .map(person -> {
                    PersonWithFireStationAndMedicalRecord personFire = new PersonWithFireStationAndMedicalRecord();
                    personFire.setFirstname(person.getFirstName());
                    personFire.setLastname(person.getLastName());
                    personFire.setAge(person.getAge());
                    personFire.setPhone(person.getPhone());
                    if (person.getFireStation() != null)
                        personFire.setFireStationNumber(String.valueOf(person.getFireStation().getStationNumber()));
                    if (person.getMedicalRecord() != null) {
                        Set<String> allergies = person.getMedicalRecord().getAllergies();
                        Set<String> medications = person.getMedicalRecord().getMedications();
                        List<String> medicalRecords = new ArrayList<>(allergies);
                        medicalRecords.addAll(medications);
                        personFire.setMedicalRecords(medicalRecords);
                    }
                    return personFire;
                }).toList();
        response.setPersonsWithFireStationAndMedicalRecords(personWithFireStationAndMedicalRecords);
        return response;
    }


    public static List<String> personsToEmails(Set<Person> persons) {
        return persons.stream().map(person -> person.getEmail()).toList();
    }


    public static List<PersonInfo> personsToPersonsInfo(Set<Person> persons) {
        return persons.stream().map(person -> {
            PersonInfo personInfo = new PersonInfo();
            personInfo.setFirstName(person.getFirstName());
            personInfo.setLastName(person.getLastName());
            personInfo.setAge(person.getAge());
            personInfo.setAddress(person.getAddress());
            personInfo.setEmail(person.getEmail());
            Set<String> medications = person.getMedicalRecord().getMedications();
            Set<String> allergies = person.getMedicalRecord().getAllergies();
            List<String> medicalRecords = new ArrayList<>(allergies);
            medicalRecords.addAll(medications);
            personInfo.setMedicalRecords(medicalRecords);
            return personInfo;
        }).toList();
    }

    public static PersonsFloodResponse personsToPersonsFloodResponse(Set<Person> persons) {
        PersonsFloodResponse response = new PersonsFloodResponse();
        Map<String, List<PersonFlood>> addressByPersonsFlood = new HashMap<>();
        for (Person person : persons) {
            final String currentAddress = person.getAddress();
            if (!addressByPersonsFlood.containsKey(currentAddress)) {
                addressByPersonsFlood.put(
                        currentAddress,
                        persons
                                .stream()
                                .filter(p -> p.getAddress().equals(currentAddress))
                                .map(p -> {
                                    PersonFlood personFlood = new PersonFlood();
                                    personFlood.setFirstName(p.getFirstName());
                                    personFlood.setLastName(p.getLastName());
                                    personFlood.setAge(p.getAge());
                                    personFlood.setPhone(p.getPhone());
                                    Set<String> medications = p.getMedicalRecord().getMedications();
                                    Set<String> allergies = p.getMedicalRecord().getAllergies();
                                    List<String> medicalRecords = new ArrayList<>(allergies);
                                    medicalRecords.addAll(medications);
                                    personFlood.setMedicalRecords(medicalRecords);
                                    return personFlood;
                                }).toList()
                );
            }
        }

        List<PersonsFloodByAddress> personsFloodByAddresses = new ArrayList<>();
        for (Map.Entry<String, List<PersonFlood>> entry : addressByPersonsFlood.entrySet()) {
            PersonsFloodByAddress personsFloodByAddress = new PersonsFloodByAddress();
            personsFloodByAddress.setAddress(entry.getKey());
            personsFloodByAddress.setPersonsFlood(entry.getValue());
            personsFloodByAddresses.add(personsFloodByAddress);
        }
        response.setPersonsFloods(personsFloodByAddresses);
        return response;
    }
}

