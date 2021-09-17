package com.example.testProject.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "salary")
    private double salary;

    @Column(name = "married")
    private boolean married;

    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    @OneToOne
    @JoinColumn(name = "auth_employee_id", referencedColumnName = "id")
    private AuthEmployee authEmployee;
}
