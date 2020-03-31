package com.disid.api.employees.models;

import com.disid.api.departments.models.Department;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="department_id")
    private Department department;

    @Column(name="age")
    private int age;

    @Column(name = "registered_at")
    private LocalDate registeredAt;

    public Employee() {
    }

    public Employee(String firstName, String lastName, Department department, int age, LocalDate registeredAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.age = age;
        this.registeredAt = registeredAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDate registeredAt) {
        this.registeredAt = registeredAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                age == employee.age &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(department, employee.department) &&
                Objects.equals(registeredAt, employee.registeredAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, department, age, registeredAt);
    }
}
