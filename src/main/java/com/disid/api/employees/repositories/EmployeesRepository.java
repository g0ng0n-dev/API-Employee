package com.disid.api.employees.repositories;

import com.disid.api.employees.models.Employee;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface EmployeesRepository extends CrudRepository<Employee, Long>, QuerydslPredicateExecutor<Employee> {

    Iterable<Employee> getEmployeeByRegisteredAtGreaterThanEqual(LocalDate date);
}
