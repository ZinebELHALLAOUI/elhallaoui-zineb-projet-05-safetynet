package app.domain.model;

import java.util.*;

public class MedicalRecord {
    private final String id;
    private final Set<String> medications;
    private final Set<String> allergies;

    public MedicalRecord(String id, Collection<String> medications, Collection<String> allergies) {
        this.id = id;
        this.medications = new HashSet<>(medications);
        this.allergies = new HashSet<>(allergies);
    }

    public MedicalRecord(String firstname,String lastname, Collection<String> medications, Collection<String> allergies) {
        this.id = generateIdFromFirstnameAndLastname(firstname, lastname);
        this.medications = new HashSet<>(medications);
        this.allergies = new HashSet<>(allergies);
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

    public String getId() {
        return id;
    }

    public Set<String> getMedications() {
        return medications;
    }

    public Set<String> getAllergies() {
        return allergies;
    }


    public static String generateIdFromFirstnameAndLastname(String firstName, String lastName) {
        return (firstName +
                "." +
                lastName)
                .toLowerCase(Locale.ROOT);
    }
}
