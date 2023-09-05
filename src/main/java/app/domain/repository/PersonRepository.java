package app.domain.repository;

import app.domain.model.Person;

import java.util.List;

public interface PersonRepository {
    List<Person> getAll();
    Person update(final Person person);
    Person add(final Person person);
    boolean deleteById(final String personId);
}
