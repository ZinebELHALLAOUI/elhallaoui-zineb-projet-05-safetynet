package service.implementation;

import model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.PersonRepository;
import service.PersonService;
import service.exception.EntityAlreadyExistException;
import service.exception.EntityNotFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonServiceImpl implements PersonService {

    private static Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Set<Person> getPersonsByFireStationNumber(final int fireStationNumber) {
        return personRepository.getAll()
                .stream()
                .filter(person -> person.getFireStation() != null)
                .filter(person -> person.getFireStation().getStationNumber().equals(fireStationNumber))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Person> getPersonsByFireStationsNumbers(Set<Integer> fireStationsNumbers) {
        return personRepository.getAll()
                .stream()
                .filter(person -> person.getFireStation() != null)
                .filter(person -> fireStationsNumbers.contains(person.getFireStation().getStationNumber()))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Person> getPersonsByFirstnameOrLastname(String firstname, String lastname) {
        return personRepository.getAll()
                .stream()
                .filter(person -> person.getFirstName().equals(firstname) || person.getLastName().equals(lastname))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Person> getPersonsByCity(String city) {
        return personRepository.getAll()
                .stream()
                .filter(person -> city.equals(person.getCity()))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Person> getChildrenByAddress(String address) {
        List<Person> persons = personRepository.getAll();
        Set<Person> children = persons
                .stream()
                .filter(person -> address.equals(person.getAddress()))
                .filter(person -> person.getBirthdate() != null && person.isMinor())
                .collect(Collectors.toSet());
        return children;
    }

    @Override
    public Set<Person> getPersonsByAddress(String address) {
        List<Person> persons = personRepository.getAll();
        logger.debug("found persons by address : " + persons.size());
        return persons
                .stream()
                .filter(person -> address.equals(person.getAddress()))
                .collect(Collectors.toSet());
    }

    @Override
    public Person addPerson(Person person) {
        if (personRepository.findPersonById(person.getId()).isPresent()) {
            throw new EntityAlreadyExistException("Person already exists");
        }
        return personRepository.addPerson(person);
    }

    @Override
    public Person updatePerson(Person person) {
        if (personRepository.findPersonById(person.getId()).isEmpty()) {
            throw new EntityNotFoundException("Person does not exist");
        }
        return personRepository.updatePerson(person);
    }

    @Override
    public boolean deletePersonById(String personId) {
        if (personRepository.findPersonById(personId).isEmpty()) {
            throw new EntityNotFoundException("Person does not exist");
        }
        final String medicalRecordId = personId;
        if (personRepository.isExistsMedicalRecordById(medicalRecordId)) {
            this.deleteMedicalRecordById(personId);
        }
        return personRepository.deletePersonById(personId);

    }

    @Override
    public Person addMedicalRecordOfPerson(Person personWithMedicalRecord) {
        if (personRepository.findPersonById(personWithMedicalRecord.getId()).isEmpty()) {
            throw new EntityNotFoundException("Person does not exist");
        }
        if (personRepository.isExistsMedicalRecordById(personWithMedicalRecord.getMedicalRecord().getId())) {
            throw new EntityAlreadyExistException("Medical record already exists");
        }
        return personRepository.addMedicalRecordOfPerson(personWithMedicalRecord);
    }

    @Override
    public boolean deleteMedicalRecordById(String medicalRecordId) {
        if (!personRepository.isExistsMedicalRecordById(medicalRecordId)) {
            throw new EntityNotFoundException("Medical record does not exist");
        }
        return personRepository.deleteMedicalRecordOfPersonById(medicalRecordId);
    }

    @Override
    public Person updateMedicalRecordOfPerson(Person personWithMedicalRecord) {
        if (!personRepository.isExistsMedicalRecordById(personWithMedicalRecord.getId())) {
            throw new EntityNotFoundException("Medical record does not exist");
        }
        return personRepository.updateMedicalRecordOfPerson(personWithMedicalRecord);
    }
}
