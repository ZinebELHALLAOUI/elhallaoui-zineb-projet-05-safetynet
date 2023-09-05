package app.infrastructure.repository;

import app.domain.model.Email;
import app.domain.model.Person;
import app.domain.model.Phone;
import app.domain.repository.PersonRepository;
import app.infrastructure.entity.FireStationEntity;
import app.infrastructure.entity.MedicalRecordEntity;
import app.infrastructure.entity.PersonEntity;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class PersonJsonRepository extends SafetyNetAlertDataJsonRepository implements PersonRepository {

    public PersonJsonRepository() {
        super();
    }

    PersonJsonRepository(final File jsonFilePath) {
        super(jsonFilePath);
    }


    @Override
    public List<Person> getAll() {
        return this.safetyNetAlertData.getPersons()
                .stream()
                .map(personEntity ->
                        {
                            final String id = personEntity.generateIdFromFirstnameAndLastname();
                            final String firstName = personEntity.getFirstName();
                            final String lastName = personEntity.getLastName();
                            final String address = personEntity.getAddress();
                            final String city = personEntity.getCity();
                            final String zip = personEntity.getZip();
                            final Phone phone = personEntity.getPhone().isBlank() ? null : new Phone(personEntity.getPhone());
                            final Email email = personEntity.getEmail().isBlank() ? null : new Email(personEntity.getEmail());
                            final LocalDate birthDate = this.findMedicalRecordByPerson(personEntity)
                                    .map(medicalRecordEntity -> LocalDate.parse(medicalRecordEntity.getBirthdate(), DateTimeFormatter.ofPattern("MM/dd/yyyy")))
                                    .orElse(null);
                            final String medicalRecordId = this.findMedicalRecordByPerson(personEntity)
                                    .map(medicalRecordEntity -> id)
                                    .orElse(null);
                            final List<Integer> fireStationsNumbers = this.findFireStationsByAddress(personEntity.getAddress())
                                    .stream()
                                    .map(fireStationEntity -> Integer.valueOf(fireStationEntity.getStation()))
                                    .toList();
                            return new Person(id, firstName, lastName, address, city, zip, phone, email, birthDate, medicalRecordId, fireStationsNumbers);
                        }
                ).toList();
    }

    @Override
    public Person update(Person person) {
        return null;
    }

    @Override
    public Person add(Person person) {
        return null;
    }

    @Override
    public boolean deleteById(String personId) {
        return false;
    }

    private Optional<MedicalRecordEntity> findMedicalRecordByPerson(final PersonEntity personEntity) {
        return this.safetyNetAlertData.getMedicalrecords()
                .stream()
                .filter(medicalRecordEntity ->
                        medicalRecordEntity.getFirstName().equals(personEntity.getFirstName()) &&
                                medicalRecordEntity.getLastName().equals(personEntity.getLastName()))
                .findFirst();
    }

    private List<FireStationEntity> findFireStationsByAddress(final String address) {
        return this.safetyNetAlertData.getFirestations()
                .stream()
                .filter(fireStationEntity -> fireStationEntity.getAddress().equals(address))
                .toList();
    }
}
