package model;

import java.util.Objects;

public class PersonWithComparableById implements Comparable<PersonWithComparableById> {

    private final String name;
    private final Integer age;

    public PersonWithComparableById(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonWithComparableById that = (PersonWithComparableById) o;
        return Objects.equals(age, that.age) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "PersonWithComparableById{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public int compareTo(PersonWithComparableById o) {
        return age.compareTo(o.getAge());
    }
}
