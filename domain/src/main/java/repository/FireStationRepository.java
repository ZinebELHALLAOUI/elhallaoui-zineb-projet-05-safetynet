package repository;


import model.FireStation;

import java.util.Optional;
import java.util.Set;

public interface FireStationRepository {
    Optional<FireStation> findByAddress (final String Address);
    Set<FireStation> findByStationNumber (final Integer stationNumber);
    FireStation add(final FireStation fireStation);
    FireStation update(final FireStation fireStation);
    boolean deleteByStationNumber(final Integer stationNumber);
    boolean deleteByAddress(final String address);
}
