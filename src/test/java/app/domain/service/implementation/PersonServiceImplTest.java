package app.domain.service.implementation;

import app.domain.model.*;
import app.domain.repository.PersonRepository;
import app.domain.service.exception.EntityAlreadyExistException;
import app.domain.service.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    public void should_return_only_persons_with_fire_station_number_3() {
        //given
        when(personRepository.getAll()).thenReturn(
                List.of(
                        new Person("fake", "fake", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, new FireStation(1, "bar")),
                        new Person("fake", "fake", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), null, null, new FireStation(3, "toto")),
                        new Person("fake", "fake", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, new FireStation(3, "foo")),
                        new Person("fake", "fake", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"))
                )
        );

        //when
        Set<Person> result = personService.getPersonsByFireStationNumber(3);

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.stream().map(person -> person.getFireStation().getStationNumber()).toList())
                .allMatch(number -> number.equals(3));
    }

    @Test
    public void should_not_return_any_person_when_no_person_has_fire_station_number_3() {
        //given
        when(personRepository.getAll()).thenReturn(
                List.of(
                        new Person("fake", "fake", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, new FireStation(1, "bar")),
                        new Person("fake", "fake", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, new FireStation(2, "toto")),
                        new Person("fake", "fake", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, new FireStation(6, "foo")),
                        new Person("fake", "fake", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, null)
                )
        );

        //when
        Set<Person> result = personService.getPersonsByFireStationNumber(3);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void should_return_only_persons_with_fire_station_numbers_3_and_1() {
        //given
        when(personRepository.getAll()).thenReturn(
                List.of(
                        new Person("fake", "fake", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, new FireStation(1, "bar")),
                        new Person("fake", "fake", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, new FireStation(3, "toto")),
                        new Person("fake", "fake", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, new FireStation(2, "foo")),
                        new Person("fake", "fake", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, null)
                )
        );

        //when
        Set<Person> result = personService.getPersonsByFireStationsNumbers(Set.of(3, 1));

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.stream().map(person -> person.getFireStation().getStationNumber()).toList())
                .allMatch(number -> number.equals(3) || number.equals(1));
    }

    @Test
    public void should_not_return_any_person_when_no_person_has_fire_station_number_3_nor_1() {
        //given
        when(personRepository.getAll()).thenReturn(
                List.of(
                        new Person("fake", "fake", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, new FireStation(5, "bar")),
                        new Person("fake", "fake", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, new FireStation(7, "toto")),
                        new Person("fake", "fake", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, null)
                )
        );

        //when
        Set<Person> result = personService.getPersonsByFireStationsNumbers(Set.of(3, 1));

        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void should_not_return_any_person_when_no_person_has_foo_as_firstname_or_bar_as_lastname() {
        //given
        when(personRepository.getAll()).thenReturn(
                List.of(
                        new Person("fake", "fake", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, new FireStation(1, "bar")),
                        new Person("fake2", "fake2", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, new FireStation(3, "toto"))
                )
        );

        //when
        Set<Person> result = personService.getPersonsByFirstnameOrLastname("foo", "bar");

        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void should_return_only_persons_with_foo_as_firstname_or_bar_as_lastname() {
        //given
        when(personRepository.getAll()).thenReturn(
                List.of(
                        new Person("foo", "fake", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, new FireStation(1, "bar")),
                        new Person("foo", "bar", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, new FireStation(3, "toto")),
                        new Person("fake", "fake", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, new FireStation(2, "foo")),
                        new Person("fake", "bar", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, null)
                )
        );

        //when
        Set<Person> result = personService.getPersonsByFirstnameOrLastname("foo", "bar");

        //then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result)
                .allMatch(person -> person.getFirstName().equals("foo") || person.getLastName().equals("bar"));
    }

    @Test
    public void should_return_only_persons_with_city_is_xCity() {
        //given
        when(personRepository.getAll()).thenReturn(
                List.of(
                        new Person("fake", "fake", null, "xCity", null, null, null, null, null, new FireStation(1, "bar")),
                        new Person("fake", "fake", null, "xCity", null, null, null, null, null, new FireStation(3, "toto")),
                        new Person("fake", "fake", null, "fake", null, null, null, null, null, new FireStation(3, "toto")),
                        new Person("fake", "fake", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, new FireStation(2, "foo"))
                )
        );

        //when
        Set<Person> result = personService.getPersonsByCity("xCity");

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result)
                .allMatch(person -> person.getCity().equals("xCity"));
    }

    @Test
    public void should_not_return_any_person_when_no_person_has_city_xCity() {
        //given
        when(personRepository.getAll()).thenReturn(
                List.of(
                        new Person("fake", "fake", null, "fake", null, null, null, null, null, new FireStation(1, "bar")),
                        new Person("fake", "fake", null, "fake2", null, null, null, null, null, new FireStation(3, "toto")),
                        new Person("fake", "fake", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, new FireStation(2, "foo"))
                )
        );

        //when
        Set<Person> result = personService.getPersonsByCity("xCity");

        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void should_return_only_persons_with_address_is_xAddress() {
        //given
        when(personRepository.getAll()).thenReturn(
                List.of(
                        new Person("fake", "fake", "fake", "xCity", null, null, null, null, null, new FireStation(1, "bar")),
                        new Person("fake", "fake", "xAddress", "xCity", null, null, null, null, null, new FireStation(3, "toto")),
                        new Person("fake", "fake", "xAddress", "fake", null, null, null, null, null, new FireStation(3, "toto")),
                        new Person("fake", "fake", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, new FireStation(2, "foo"))
                )
        );

        //when
        Set<Person> result = personService.getPersonsByAddress("xAddress");

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result)
                .allMatch(person -> person.getAddress().equals("xAddress"));
    }

    @Test
    public void should_not_return_any_person_when_no_person_has_address_xAddress() {
        //given
        when(personRepository.getAll()).thenReturn(
                List.of(
                        new Person("fake", "fake", "fake", "xCity", null, null, null, null, null, new FireStation(1, "bar")),
                        new Person("fake", "fake", "fake2", "xCity", null, null, null, null, null, new FireStation(3, "toto")),
                        new Person("fake", "fake", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, new FireStation(2, "foo"))
                )
        );

        //when
        Set<Person> result = personService.getPersonsByAddress("xAddress");

        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void should_return_only_children_with_address_is_xAddress() {
        //given
        when(personRepository.getAll()).thenReturn(
                List.of(
                        new Person("fake", "fake", "fake", "xCity", null, null, null, LocalDate.now(), null, new FireStation(1, "bar")),
                        new Person("fake", "fake", "xAddress", "xCity", null, null, null, LocalDate.now(), null, new FireStation(3, "toto")),
                        new Person("fake", "fake", "xAddress", "fake", null, null, null, LocalDate.now().minusYears(20), null, new FireStation(3, "toto")),
                        new Person("fake", "fake", "xAddress", null, null, null, null, null, null, new FireStation(2, "foo")),
                        new Person("fake", "fake", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, new FireStation(2, "foo")),
                        new Person("fake", "fake", null, null, null, null, null, LocalDate.now(), null, new FireStation(2, "foo"))
                )
        );

        //when
        Set<Person> result = personService.getChildrenByAddress("xAddress");

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result)
                .allMatch(person -> person.getAddress().equals("xAddress") && person.isMinor());
    }

    @Test
    public void should_not_return_any_person_when_no_person_has_address_fakeAddress_and_he_is_minor() {
        //given
        when(personRepository.getAll()).thenReturn(
                List.of(
                        new Person("fake", "fake", "fake", "xCity", null, null, null, LocalDate.now(), null, new FireStation(1, "bar")),
                        new Person("fake", "fake", "xAddress", "fake", null, null, null, LocalDate.now().minusYears(20), null, new FireStation(3, "toto")),
                        new Person("fake", "fake", "xAddress", null, null, null, null, null, null, new FireStation(2, "foo")),
                        new Person("fake", "fake", "address", "paris", "75000", new Phone("841-874-7512"), new Email("test@email.com"), LocalDate.now(), null, new FireStation(2, "foo")),
                        new Person("fake", "fake", null, null, null, null, null, LocalDate.now(), null, new FireStation(2, "foo"))
                )
        );

        //when
        Set<Person> result = personService.getChildrenByAddress("xAddress");

        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void should_throw_exception_when_adding_existing_person() {
        //given
        final Person person = new Person("fake", "fake", "fake", "xCity", null, null, null, LocalDate.now(), null, new FireStation(1, "bar"));
        when(personRepository.findPersonById(person.getId())).thenReturn(Optional.of(person));

        //when then
        assertThatThrownBy(() -> personService.addPerson(person))
                .isInstanceOf(EntityAlreadyExistException.class)
                .hasMessage("Person already exists");
    }

    @Test
    public void should_add_person_when_person_does_not_exist() {
        //given
        final Person person = new Person("fake", "fake", "fake", "xCity", null, null, null, LocalDate.now(), null, new FireStation(1, "bar"));
        when(personRepository.findPersonById(person.getId())).thenReturn(Optional.empty());

        //when
        personService.addPerson(person);

        // then
        verify(personRepository).addPerson(person);
    }

    @Test
    public void should_throw_exception_when_updating_not_existing_person() {
        //given
        final Person person = new Person("fake", "fake", "fake", "xCity", null, null, null, LocalDate.now(), null, new FireStation(1, "bar"));
        when(personRepository.findPersonById(person.getId())).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> personService.updatePerson(person))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Person does not exist");
    }

    @Test
    public void should_update_person_when_person_exists() {
        //given
        final Person person = new Person("fake", "fake", "fake", "xCity", null, null, null, LocalDate.now(), null, new FireStation(1, "bar"));
        when(personRepository.findPersonById(person.getId())).thenReturn(Optional.of(person));

        //when
        personService.updatePerson(person);

        // then
        verify(personRepository).updatePerson(person);
    }

    @Test
    public void should_throw_exception_when_deleting_not_existing_person() {
        //given
        final Person person = new Person("fake", "fake", "fake", "xCity", null, null, null, LocalDate.now(), null, new FireStation(1, "bar"));
        when(personRepository.findPersonById(person.getId())).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> personService.deletePersonById(person.getId()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Person does not exist");
    }

    @Test
    public void should_delete_when_person_exists_and_not_trigger_delete_medical_record_if_it_does_not_exist() {
        //given
        final Person person = new Person("fake", "fake", "fake", "xCity", null, null, null, LocalDate.now(), null, new FireStation(1, "bar"));
        when(personRepository.findPersonById(person.getId())).thenReturn(Optional.of(person));
        when(personRepository.isExistsMedicalRecordById(person.getId())).thenReturn(false);

        //when
        personService.deletePersonById(person.getId());

        // then
        verify(personRepository).deletePersonById(person.getId());
        verify(personRepository, times(0)).deleteMedicalRecordOfPersonById(person.getId());
    }

    @Test
    public void should_delete_when_person_exists_and_trigger_delete_medical_record_if_it_exists() {
        //given
        final Person person = new Person("fake", "fake", "fake", "xCity", null, null, null, LocalDate.now(), null, new FireStation(1, "bar"));
        when(personRepository.findPersonById(person.getId())).thenReturn(Optional.of(person));
        when(personRepository.isExistsMedicalRecordById(person.getId())).thenReturn(true);

        //when
        personService.deletePersonById(person.getId());

        // then
        verify(personRepository).deleteMedicalRecordOfPersonById(person.getId());
        verify(personRepository).deletePersonById(person.getId());
    }

    @Test
    public void should_throw_exception_when_adding_medical_record_but_person_does_not_existing() {
        //given
        final Person personWithMedicalRecord = new Person("fake", "fake", "fake", "xCity", null, null, null, LocalDate.now(), null, new FireStation(1, "bar"));
        when(personRepository.findPersonById(personWithMedicalRecord.getId())).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> personService.addMedicalRecordOfPerson(personWithMedicalRecord))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Person does not exist");
    }

    @Test
    public void should_throw_exception_when_adding_existing_medical_record() {
        //given
        final Person personWithMedicalRecord = new Person("fake", "fake", "fake", "xCity", null, null, null, LocalDate.now(), new MedicalRecord("fake", "fake", List.of(), List.of()), new FireStation(1, "bar"));
        when(personRepository.findPersonById(personWithMedicalRecord.getId())).thenReturn(Optional.of(personWithMedicalRecord));
        when(personRepository.isExistsMedicalRecordById(personWithMedicalRecord.getId())).thenReturn(true);

        //when then
        assertThatThrownBy(() -> personService.addMedicalRecordOfPerson(personWithMedicalRecord))
                .isInstanceOf(EntityAlreadyExistException.class)
                .hasMessage("Medical record already exists");
    }

    @Test
    public void should_add_medical_record_when_medical_record_does_not_exist() {
        //given
        final Person personWithMedicalRecord = new Person("fake", "fake", "03/06/2014", List.of("fake", "fake"), List.of("fake", "fake"));
        when(personRepository.findPersonById(personWithMedicalRecord.getId())).thenReturn(Optional.of(personWithMedicalRecord));
        when(personRepository.isExistsMedicalRecordById(personWithMedicalRecord.getId())).thenReturn(false);

        //when
        personService.addMedicalRecordOfPerson(personWithMedicalRecord);

        // then
        verify(personRepository).addMedicalRecordOfPerson(personWithMedicalRecord);
    }

    @Test
    public void should_throw_exception_when_deleting_not_existing_medical_record() {
        //given
        final Person personWithMedicalRecord = new Person("fake", "fake", "fake", "xCity", null, null, null, LocalDate.now(), new MedicalRecord("fake", "fake", List.of(), List.of()), new FireStation(1, "bar"));
        when(personRepository.isExistsMedicalRecordById(personWithMedicalRecord.getId())).thenReturn(false);

        //when then
        assertThatThrownBy(() -> personService.deleteMedicalRecordById(personWithMedicalRecord.getMedicalRecord().getId()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Medical record does not exist");
    }

    @Test
    public void should_deleting_existing_medical_record() {
        //given
        final Person personWithMedicalRecord = new Person("fake", "fake", "fake", "xCity", null, null, null, LocalDate.now(), new MedicalRecord("fake", "fake", List.of(), List.of()), new FireStation(1, "bar"));
        when(personRepository.isExistsMedicalRecordById(personWithMedicalRecord.getId())).thenReturn(true);

        //when
        personService.deleteMedicalRecordById(personWithMedicalRecord.getMedicalRecord().getId());

        // then
        verify(personRepository).deleteMedicalRecordOfPersonById(personWithMedicalRecord.getMedicalRecord().getId());
    }

    @Test
    public void should_throw_exception_when_updating_not_existing_medical_record() {
        //given
        final Person personWithMedicalRecord = new Person("fake", "fake", "03/06/2014", List.of("fake", "fake"), List.of("fake", "fake"));
        when(personRepository.isExistsMedicalRecordById(personWithMedicalRecord.getId())).thenReturn(false);

        //when then
        assertThatThrownBy(() -> personService.updateMedicalRecordOfPerson(personWithMedicalRecord))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Medical record does not exist");
    }

    @Test
    public void should_updating_existing_medical_record() {
        //given
        final Person personWithMedicalRecord = new Person("fake", "fake", "03/06/2014", List.of("fake", "fake"), List.of("fake", "fake"));
        when(personRepository.isExistsMedicalRecordById(personWithMedicalRecord.getId())).thenReturn(true);

        //when
        personService.updateMedicalRecordOfPerson(personWithMedicalRecord);

        // then
        verify(personRepository).updateMedicalRecordOfPerson(personWithMedicalRecord);
    }


}