package app.infrastructure.controller.dto.response;

import java.util.List;
import java.util.Objects;

public class PersonsByFireStationResponse {
    private List<PersonByFireStation> persons;
    private long numberOfAdults;
    private long numberOfMinors;

    public List<PersonByFireStation> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonByFireStation> persons) {
        this.persons = persons;
    }

    public long getNumberOfAdults() {
        return numberOfAdults;
    }

    public void setNumberOfAdults(long numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    public long getNumberOfMinors() {
        return numberOfMinors;
    }

    public void setNumberOfMinors(long numberOfMinors) {
        this.numberOfMinors = numberOfMinors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonsByFireStationResponse that = (PersonsByFireStationResponse) o;
        return numberOfAdults == that.numberOfAdults && numberOfMinors == that.numberOfMinors && Objects.equals(persons, that.persons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, numberOfAdults, numberOfMinors);
    }
}
