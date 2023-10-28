package app.controller.dto.response;

import java.util.List;
import java.util.Objects;

public class PersonsFloodResponse {
    private List<PersonsFloodByAddress> personsFloodByAddresses;

    public List<PersonsFloodByAddress> getPersonsFloods() {
        return personsFloodByAddresses;
    }

    public void setPersonsFloods(List<PersonsFloodByAddress> personsFloodByAddresses) {
        this.personsFloodByAddresses = personsFloodByAddresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonsFloodResponse that = (PersonsFloodResponse) o;
        return Objects.equals(personsFloodByAddresses, that.personsFloodByAddresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personsFloodByAddresses);
    }
}
