package app.infrastructure.controller.dto.person;

import java.util.List;

public class PersonsFloodResponse {
    private List<PersonsFloodByAddress> personsFloodByAddresses;

    public List<PersonsFloodByAddress> getPersonsFloods() {
        return personsFloodByAddresses;
    }

    public void setPersonsFloods(List<PersonsFloodByAddress> personsFloodByAddresses) {
        this.personsFloodByAddresses = personsFloodByAddresses;
    }

}
