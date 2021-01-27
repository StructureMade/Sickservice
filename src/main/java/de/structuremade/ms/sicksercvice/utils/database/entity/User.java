package de.structuremade.ms.sicksercvice.utils.database.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users", schema = "services", indexes = {
        @Index(name = "id_userid", columnList = "id", unique = true),
        @Index(name = "id_token", columnList = "token", unique = true),
        @Index(name = "id_email", columnList = "email", unique = true)})
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String name;

    @Column
    private String password;

    @Column
    private String abbreviation;

    @Column(nullable = false)
    private Date creationDate;

    @Column
    private String token;

    @Column(nullable = false)
    private boolean verified;

    @Column
    private String lastSchool;

    @ManyToMany(targetEntity = User.class)
    @JoinTable(name = "userparents", schema = "services", joinColumns = @JoinColumn(name = "parent", foreignKey = @ForeignKey(name = "fk_parent"))
            , inverseJoinColumns = @JoinColumn(name = "student", foreignKey = @ForeignKey(name = "fk_student")))
    private List<User> childrens;

    @OneToMany(targetEntity = Sick.class)
    @JoinColumn(name = "student")
    private List<Sick> sicks;
}