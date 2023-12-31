package app.repository;

import app.entity.FireStationEntity;
import app.mapper.FireStationMapper;
import model.FireStation;
import org.springframework.stereotype.Repository;
import repository.FireStationRepository;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class FireStationJsonRepository extends SafetyNetAlertDataJsonRepository implements FireStationRepository {

    public FireStationJsonRepository() {
        super();
    }

    FireStationJsonRepository(final File jsonFilePath) {
        super(jsonFilePath);
    }

    @Override
    public Optional<FireStation> findByAddress(String address) {
        return this.safetyNetAlertData.getFirestations()
                .stream()
                .map(entity -> FireStationMapper.fireStationEntityToFireStation(entity))
                .filter(fireStation -> fireStation.getAddress().equals(address))
                .findFirst();
    }

    @Override
    public Set<FireStation> findByStationNumber(Integer stationNumber) {
        return this.safetyNetAlertData.getFirestations()
                .stream()
                .map(entity -> FireStationMapper.fireStationEntityToFireStation(entity))
                .filter(fireStation -> fireStation.getStationNumber().equals(stationNumber))
                .collect(Collectors.toSet());
    }

    @Override
    public FireStation add(FireStation fireStation) {
        FireStationEntity fireStationEntity = FireStationMapper.fireStationToFireStationEntity(fireStation);
        this.safetyNetAlertData.getFirestations().add(fireStationEntity);
        this.synchronizeSafetyNetAlertData();
        return fireStation;
    }

    @Override
    public FireStation update(FireStation fireStation) {
        deleteByAddress(fireStation.getAddress());
        return add(fireStation);
    }

    @Override
    public boolean deleteByStationNumber(Integer stationNumber) {
        Set<FireStation> fireStations = this.findByStationNumber(stationNumber);
        if (!fireStations.isEmpty()) {
            List<FireStationEntity> fireStationsEntitiesToBeRemoved = fireStations.stream().map(fireStation -> FireStationMapper.fireStationToFireStationEntity(fireStation)).toList();
            this.safetyNetAlertData.getFirestations().removeAll(fireStationsEntitiesToBeRemoved);
            this.synchronizeSafetyNetAlertData();
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteByAddress(String address) {
        Optional<FireStation> fireStation = findByAddress(address);
        if (fireStation.isPresent()) {
            FireStationEntity fireStationEntity = FireStationMapper.fireStationToFireStationEntity(fireStation.get());
            this.safetyNetAlertData.getFirestations().remove(fireStationEntity);
            this.synchronizeSafetyNetAlertData();
            return true;
        }
        return false;
    }
}
