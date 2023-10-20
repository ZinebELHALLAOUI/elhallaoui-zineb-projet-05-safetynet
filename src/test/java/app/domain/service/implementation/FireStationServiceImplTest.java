package app.domain.service.implementation;

import app.domain.model.FireStation;
import app.domain.repository.FireStationRepository;
import app.domain.service.exception.EntityAlreadyExistException;
import app.domain.service.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FireStationServiceImplTest {
    @Mock
    private FireStationRepository fireStationRepository;

    @InjectMocks
    private FireStationServiceImpl fireStationService;

    @Test
    public void should_add_fireStation_when_does_not_exist() {
        //given
        final FireStation fireStation = new FireStation(1, "fake");
        when(fireStationRepository.findByAddress(fireStation.getAddress())).thenReturn(Optional.empty());
        //when
        fireStationService.add(fireStation);

        //then
        verify(fireStationRepository).add(fireStation);
    }

    @Test
    public void should_throw_exception_when_adding_existing_fireStation() {
        //given
        final FireStation fireStation = new FireStation(1, "fake");
        when(fireStationRepository.findByAddress(fireStation.getAddress())).thenReturn(Optional.of(new FireStation(1, "fake")));

        //when then
        assertThatThrownBy(() -> fireStationService.add(fireStation))
                .isInstanceOf(EntityAlreadyExistException.class)
                .hasMessage("Fire station already exists");
    }

    @Test
    public void should_update_existing_fireStation() {
        //given
        final FireStation fireStation = new FireStation(1, "fake");
        when(fireStationRepository.findByAddress(fireStation.getAddress())).thenReturn(Optional.of(fireStation));
        //when
        fireStationService.update(fireStation);

        //then
        verify(fireStationRepository).update(fireStation);
    }

    @Test
    public void should_throw_exception_when_updating_not_existing_fireStation() {
        //given
        final FireStation fireStation = new FireStation(1, "fake");
        when(fireStationRepository.findByAddress(fireStation.getAddress())).thenReturn(Optional.empty());


        //when then
        assertThatThrownBy(() -> fireStationService.update(fireStation))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Fire station does not exist");
    }

    @Test
    public void should_delete_by_station_number_existing_fireStation() {
        //given
        final Integer fireStationNumber = 1;
        when(fireStationRepository.findByStationNumber(fireStationNumber)).thenReturn(Set.of(new FireStation(fireStationNumber, "fake")));
        //when
        fireStationService.deleteByStationNumber(fireStationNumber);

        //then
        verify(fireStationRepository).deleteByStationNumber(1);
    }

    @Test
    public void should_throw_exception_when_deleting_not_existing_fireStation_by_station_number() {
        //given
        final FireStation fireStation = new FireStation(1, "fake");
        when(fireStationRepository.findByStationNumber(fireStation.getStationNumber())).thenReturn(Set.of());


        //when then
        assertThatThrownBy(() -> fireStationService.deleteByStationNumber(1))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Fire station(s) do not exist");
    }

    @Test
    public void should_delete_by_station_address_existing_fireStation() {
        //given
        FireStation fireStation = new FireStation(1, "fireStation");
        when(fireStationRepository.findByAddress(fireStation.getAddress())).thenReturn(Optional.of(fireStation));
        //when
        fireStationService.deleteByAddress(fireStation.getAddress());

        //then
        verify(fireStationRepository).deleteByAddress("fireStation");
    }

    @Test
    public void should_throw_exception_when_deleting_not_existing_fireStation_by_station_address() {
        //given
        final FireStation fireStation = new FireStation(1, "fake");
        when(fireStationRepository.findByAddress(fireStation.getAddress())).thenReturn(Optional.empty());


        //when then
        assertThatThrownBy(() -> fireStationService.deleteByAddress(fireStation.getAddress()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Fire station does not exist");
    }


}