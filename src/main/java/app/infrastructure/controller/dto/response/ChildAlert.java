package app.infrastructure.controller.dto.response;

import app.infrastructure.controller.dto.response.PersonDto;

import java.util.List;
import java.util.Objects;

public class ChildAlert {
    private String firstName;
    private String lastName;
    private int age;
    private List<PersonDto> members;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<PersonDto> getMembers() {
        return members;
    }

    public void setMembers(List<PersonDto> members) {
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChildAlert that = (ChildAlert) o;
        return age == that.age && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(members, that.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, members);
    }
}
