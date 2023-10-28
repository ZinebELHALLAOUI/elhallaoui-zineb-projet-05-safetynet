package app.controller.dto.response;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonsFloodByAddress that = (PersonsFloodByAddress) o;
        return Objects.equals(Address, that.Address) && Objects.equals(personsFlood, that.personsFlood);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Address, personsFlood);
    }
}
