package repository;


import model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    List<Person> getAll();

    Optional<Person> findPersonById(final String personId);

    Person addPerson(final Person person);

    Person updatePerson(final Person person);

    boolean deletePersonById(final String personId);

    Person addMedicalRecordOfPerson(Person person);

    boolean isExistsMedicalRecordById(String id);

    boolean deleteMedicalRecordOfPersonById(String medicalRecordId);

    Person updateMedicalRecordOfPerson(final Person person);
}
