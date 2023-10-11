package app.domain.model;

import java.util.Objects;

public class Email {

    @org.hibernate.validator.constraints.Email
    private final String mail;

    public Email(@org.hibernate.validator.constraints.Email String mail) {
        this.mail = mail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(mail, email.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mail);
    }

    @Override
    public String toString() {
        return "Email{" +
                "mail='" + mail + '\'' +
                '}';
    }

    public String getMail() {
        return mail;
    }
}
