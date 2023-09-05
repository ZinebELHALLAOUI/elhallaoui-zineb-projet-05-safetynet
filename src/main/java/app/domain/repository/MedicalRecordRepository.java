package app.domain.repository;

import app.domain.model.MedicalRecord;
import app.domain.model.Person;

import java.util.List;

public interface MedicalRecordRepository {
    List<MedicalRecord> findByPerson(final Person person);
    MedicalRecord add(final MedicalRecord medicalRecord);
    MedicalRecord update(final MedicalRecord medicalRecord);

    boolean deleteById(final String id);
}
