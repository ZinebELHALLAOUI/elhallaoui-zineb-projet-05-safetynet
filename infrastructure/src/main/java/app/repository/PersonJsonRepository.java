package app.repository;

import app.entity.FireStationEntity;
import app.entity.MedicalRecordEntity;
import app.entity.PersonEntity;
import app.mapper.FireStationMapper;
import app.mapper.PersonMapper;
import model.FireStation;
import model.MedicalRecord;
import model.Person;
import org.springframework.stereotype.Repository;
import repository.PersonRepository;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Repository
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
                            final String firstName = personEntity.getFirstName();
                            final String lastName = personEntity.getLastName();
                            final String address = personEntity.getAddress();
                            final String city = personEntity.getCity();
                            final String zip = personEntity.getZip();
                            final String phone = personEntity.getPhone();
                            final String email = personEntity.getEmail();
                            Optional<MedicalRecordEntity> medicalRecordByPerson = this.findMedicalRecordByPerson(personEntity);
                            MedicalRecord medicalRecord = medicalRecordByPerson.map(entity -> PersonMapper.entityToMedicalRecord(entity)).orElse(null);
                            final LocalDate birthDate = medicalRecordByPerson
                                    .map(medicalRecordEntity -> LocalDate.parse(medicalRecordEntity.getBirthdate(), DateTimeFormatter.ofPattern("MM/dd/yyyy")))
                                    .orElse(null);


                            Optional<FireStationEntity> optionalFireStation = this.findFireStationsByAddress(personEntity.getAddress());
                            FireStation fireStation = FireStationMapper.fireStationEntityToFireStation(optionalFireStation.orElse(null));

                            return new Person(firstName, lastName, address, city, zip, phone, email, birthDate, medicalRecord, fireStation);
                        }
                ).toList();
    }

    @Override
    public Optional<Person> findPersonById(final String personId) {
        return this.getAll().stream().filter(person -> person.getId().equals(personId)).findFirst();
    }

    @Override
    public Person updatePerson(Person person) {
        this.deletePersonById(person.getId());
        return this.addPerson(person);
    }

    @Override
    public Person addPerson(Person person) {
        PersonEntity personEntity = PersonMapper.PersonToPersonEntity(person);
        this.safetyNetAlertData.getPersons().add(personEntity);
        this.synchronizeSafetyNetAlertData();
        return person;
    }

    @Override
    public boolean deletePersonById(String personId) {
        Optional<Person> personToDelete = this.findPersonById(personId);
        if (personToDelete.isPresent()) {
            PersonEntity personEntityToDelete = PersonMapper.PersonToPersonEntity(personToDelete.get());
            this.safetyNetAlertData.getPersons().remove(personEntityToDelete);
            this.synchronizeSafetyNetAlertData();
            return true;
        }
        return false;
    }

    @Override
    public Person addMedicalRecordOfPerson(Person person) {
        MedicalRecordEntity medicalRecordOfPerson = PersonMapper.getMedicalRecordOfPerson(person);
        this.safetyNetAlertData.getMedicalrecords().add(medicalRecordOfPerson);
        this.synchronizeSafetyNetAlertData();
        return person;
    }

    @Override
    public boolean isExistsMedicalRecordById(String id) {
        return this.safetyNetAlertData.getMedicalrecords()
                .stream()
                .anyMatch(medicalRecordEntity -> (medicalRecordEntity.getFirstName() + "." + medicalRecordEntity.getLastName()).equalsIgnoreCase(id));
    }


    @Override
    public boolean deleteMedicalRecordOfPersonById(String medicalRecordId) {
        if (this.isExistsMedicalRecordById(medicalRecordId)) {
            Optional<Person> person = this.findPersonById(medicalRecordId);
            MedicalRecordEntity medicalRecordToDelete = PersonMapper.getMedicalRecordOfPerson(person.get());
            this.safetyNetAlertData.getMedicalrecords().remove(medicalRecordToDelete);
            this.synchronizeSafetyNetAlertData();
            return true;
        }
        return false;
    }

    @Override
    public Person updateMedicalRecordOfPerson(Person person) {
        this.deleteMedicalRecordOfPersonById(person.getMedicalRecord().getId());
        return this.addMedicalRecordOfPerson(person);
    }

    private Optional<MedicalRecordEntity> findMedicalRecordByPerson(final PersonEntity personEntity) {
        return this.safetyNetAlertData.getMedicalrecords()
                .stream()
                .filter(medicalRecordEntity ->
                        medicalRecordEntity.getFirstName().equals(personEntity.getFirstName()) &&
                                medicalRecordEntity.getLastName().equals(personEntity.getLastName()))
                .findFirst();
    }

    private Optional<FireStationEntity> findFireStationsByAddress(final String address) {
        return this.safetyNetAlertData.getFirestations()
                .stream()
                .filter(fireStationEntity -> fireStationEntity.getAddress().equals(address))
                .findFirst();
    }
}
