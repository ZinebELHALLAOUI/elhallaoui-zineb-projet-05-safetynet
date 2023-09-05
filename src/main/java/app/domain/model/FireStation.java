package app.domain.model;

import java.util.Objects;

public class FireStation {
    private final Integer stationNumber;
    private final String address;

    public FireStation(Integer stationNumber, String address) {
        this.stationNumber = stationNumber;
        this.address = address;
    }

    public Integer getStationNumber() {
        return stationNumber;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FireStation that = (FireStation) o;
        return Objects.equals(stationNumber, that.stationNumber) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationNumber, address);
    }

    @Override
    public String toString() {
        return "FireStation{" +
                "id=" + stationNumber +
                ", address='" + address + '\'' +
                '}';
    }
}
