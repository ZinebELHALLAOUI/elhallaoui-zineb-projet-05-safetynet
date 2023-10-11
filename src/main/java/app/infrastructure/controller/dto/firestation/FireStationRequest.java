package app.infrastructure.controller.dto.firestation;

public class FireStationRequest {
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
}
