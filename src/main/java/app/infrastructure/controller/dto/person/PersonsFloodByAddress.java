package app.infrastructure.controller.dto.person;

import java.util.List;

public class PersonsFloodByAddress {
    private String Address;
    private List<PersonFlood> personsFlood;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public List<PersonFlood> getPersonsFlood() {
        return personsFlood;
    }

    public void setPersonsFlood(List<PersonFlood> personsFlood) {
        this.personsFlood = personsFlood;
    }

}
