package com.disid.api.departments.repositories;

import com.disid.api.departments.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DepartmentsRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department> {

    boolean existsByName(String name);

}
