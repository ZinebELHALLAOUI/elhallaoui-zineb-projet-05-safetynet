package service.implementation;


import model.FireStation;
import repository.FireStationRepository;
import service.FireStationService;
import service.exception.EntityAlreadyExistException;
import service.exception.EntityNotFoundException;

public class FireStationServiceImpl implements FireStationService {

    private final FireStationRepository fireStationRepository;

    public FireStationServiceImpl(FireStationRepository fireStationRepository) {
        this.fireStationRepository = fireStationRepository;
    }

    @Override
    public FireStation add(FireStation fireStation) {
        if (this.fireStationRepository.findByAddress(fireStation.getAddress()).isPresent()) {
            throw new EntityAlreadyExistException("Fire station already exists");
        }
        return this.fireStationRepository.add(fireStation);
    }

    @Override
    public FireStation update(FireStation fireStation) {
        if (this.fireStationRepository.findByAddress(fireStation.getAddress()).isEmpty()) {
            throw new EntityNotFoundException("Fire station does not exist");
        }
        return this.fireStationRepository.update(fireStation);
    }

    @Override
    public boolean deleteByStationNumber(Integer stationNumber) {
        if (this.fireStationRepository.findByStationNumber(stationNumber).isEmpty()) {
            throw new EntityNotFoundException("Fire station(s) do not exist");
        }
        return fireStationRepository.deleteByStationNumber(stationNumber);
    }

    @Override
    public boolean deleteByAddress(String address) {
        if (this.fireStationRepository.findByAddress(address).isEmpty()) {
            throw new EntityNotFoundException("Fire station does not exist");
        }
        return fireStationRepository.deleteByAddress(address);
    }

}
