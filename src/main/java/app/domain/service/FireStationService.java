package app.domain.service;

import app.domain.model.FireStation;

public interface FireStationService {
    FireStation add(final FireStation fireStation);

    FireStation update(final FireStation fireStation);

    boolean deleteByStationNumber(final Integer stationNumber);
    boolean deleteByAddress(final String address);
}
