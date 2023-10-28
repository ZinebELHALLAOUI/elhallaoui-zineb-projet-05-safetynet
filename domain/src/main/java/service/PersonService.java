package service;


import model.Person;

import java.util.Set;

public interface PersonService {
    Set<Person> getPersonsByFireStationNumber(final int fireStationNumber);

    Set<Person> getPersonsByFireStationsNumbers(final Set<Integer> fireStationsNumbers);

    Set<Person> getPersonsByFirstnameOrLastname(final String firstname, final String lastname);

    Set<Person> getPersonsByCity(final String city);

    Set<Person> getChildrenByAddress(final String address);

    Set<Person> getPersonsByAddress(final String address);

    Person addPerson(final Person person);

    Person updatePerson(final Person person);

    boolean deletePersonById(final String personId);

    Person addMedicalRecordOfPerson(final Person personWithMedicalRecord);

    boolean deleteMedicalRecordById(String medicalRecordId);

    Person updateMedicalRecordOfPerson(Person personWithMedicalRecord);
}
