package app.infrastructure.controller.dto.person;

import java.util.List;

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
    public String toString() {
        return "ChildAlert{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", members=" + members +
                '}';
    }
}
