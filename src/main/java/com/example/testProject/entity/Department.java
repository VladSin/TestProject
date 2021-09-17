package com.example.testProject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "website")
    private String website;

    @Column(name = "location")
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "department_employee",
//            joinColumns = {@JoinColumn(name = "department_id")},
//            inverseJoinColumns = {@JoinColumn(name = "employee_id")})
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees;
}
