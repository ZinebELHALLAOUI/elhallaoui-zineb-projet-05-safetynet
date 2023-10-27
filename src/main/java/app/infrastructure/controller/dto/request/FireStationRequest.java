package app.infrastructure.controller.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class FireStationRequest {
    @NotNull private Integer stationNumber;
    @NotEmpty private String address;

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
    public String toString() {
        return "FireStationRequest{" +
                "stationNumber=" + stationNumber +
                ", address='" + address + '\'' +
                '}';
    }
}
