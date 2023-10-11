package app.infrastructure.mapper;

import app.domain.model.Email;
import app.domain.model.MedicalRecord;
import app.domain.model.Person;
import app.domain.model.Phone;
import app.infrastructure.controller.dto.medicalRecord.MedicalRecordResponse;
import app.infrastructure.controller.dto.person.*;
import app.infrastructure.entity.MedicalRecordEntity;
import app.infrastructure.entity.PersonEntity;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PersonMapper {
    public static PersonEntity PersonToPersonEntity(final Person person) {
        if (person == null) return null;
        PersonEntity entity = new PersonEntity();
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setCity(person.getCity());
        entity.setPhone(person.getPhone().getNumber());
        entity.setZip(person.getZip());
        entity.setEmail(person.getEmail().getMail());
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
                new Phone(personAddRequest.getPhone()),
                new Email(personAddRequest.getEmail())
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
        response.setPhone(person.getPhone().getNumber());
        response.setZip(person.getZip());
        response.setEmail(person.getEmail().getMail());
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
                    personByFireStation.setPhone(person.getPhone().getNumber());
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
}
