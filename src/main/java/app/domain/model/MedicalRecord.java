package app.domain.model;

import java.util.List;
import java.util.Objects;

public class MedicalRecord {
    private final String id;
    private final List<MedicalBackGround> medications;
    private final List<MedicalBackGround> allergies;

    public MedicalRecord(String id, List<MedicalBackGround> medications, List<MedicalBackGround> allergie) {
        this.id = id;
        this.medications = medications;
        this.allergies = allergie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalRecord that = (MedicalRecord) o;
        return Objects.equals(id, that.id) && Objects.equals(medications, that.medications) && Objects.equals(allergies, that.allergies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, medications, allergies);
    }

    @Override
    public String toString() {
        return "MedicalRecord{" +
                "Id='" + id + '\'' +
                ", medications=" + medications +
                ", allergie=" + allergies +
                '}';
    }
}
