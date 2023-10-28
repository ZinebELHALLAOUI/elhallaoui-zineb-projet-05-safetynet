package app.entity;

import java.util.Objects;

public class FireStationEntity {
    private String address;
    private String station;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FireStationEntity that = (FireStationEntity) o;
        return Objects.equals(address, that.address) && Objects.equals(station, that.station);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, station);
    }
}
