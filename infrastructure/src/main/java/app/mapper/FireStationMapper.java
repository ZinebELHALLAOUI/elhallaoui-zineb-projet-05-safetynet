package app.mapper;

import app.controller.dto.response.FireStationReponse;
import app.entity.FireStationEntity;
import model.FireStation;

public class FireStationMapper {
    public static FireStation fireStationEntityToFireStation(final FireStationEntity entity) {
        if (entity == null) {
            return null;
        }
        return new FireStation(Integer.valueOf(entity.getStation()), entity.getAddress());
    }

    public static FireStationEntity fireStationToFireStationEntity(final FireStation fireStation) {
        if (fireStation == null) {
            return null;
        }
        FireStationEntity fireStationEntity = new FireStationEntity();
        fireStationEntity.setStation(String.valueOf(fireStation.getStationNumber()));
        fireStationEntity.setAddress(fireStation.getAddress());
        return fireStationEntity;
    }

    public static FireStationReponse fireStationToResponse(final FireStation fireStation) {
        if (fireStation == null) {
            return null;
        }
        FireStationReponse fireStationReponse = new FireStationReponse();
        fireStationReponse.setStationNumber(fireStation.getStationNumber());
        fireStationReponse.setAddress(fireStation.getAddress());
        return fireStationReponse;
    }
}
