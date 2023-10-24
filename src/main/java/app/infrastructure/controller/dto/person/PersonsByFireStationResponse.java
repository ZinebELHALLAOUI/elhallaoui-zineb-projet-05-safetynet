package app.infrastructure.controller.dto.person;

import java.util.List;

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
    public String toString() {
        return "PersonsByFireStationResponse{" +
                "persons=" + persons +
                ", numberOfAdults=" + numberOfAdults +
                ", numberOfMinors=" + numberOfMinors +
                '}';
    }
}
