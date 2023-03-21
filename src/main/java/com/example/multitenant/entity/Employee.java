package com.example.multitenant.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "department_id")
    private Long departmentId;

    // Constructors, getters and setters omitted for brevity

}












