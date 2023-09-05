package app.domain.repository;

import app.domain.model.FireStation;

public interface FireStationRepository {
    FireStation add(final FireStation fireStation);
    FireStation update(final FireStation fireStation);
    boolean deleteByStationNumber(final Integer stationNumber);
}
