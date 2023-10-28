package app.controller.dto.response;

import java.util.Objects;

public class FireStationReponse {
    private Integer stationNumber;
    private String address;

    public Integer getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(Integer stationNumber) {
        this.stationNumber = stationNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FireStationReponse that = (FireStationReponse) o;
        return Objects.equals(stationNumber, that.stationNumber) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationNumber, address);
    }
}
