package app.domain.model;

public abstract class MedicalBackGround {
    private final String indication;

    public MedicalBackGround(String indication) {
        this.indication = indication;
    }

    public String getIndication() {
        return indication;
    }

}
