package app.domain.model;

import java.util.Objects;

public class Phone {
    private final String number;

    public Phone(String number) {
        //TODO ADD VALIDATOR
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return Objects.equals(number, phone.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "Phone{" +
                "number='" + number + '\'' +
                '}';
    }

    public String getNumber() {
        return number;
    }
}
