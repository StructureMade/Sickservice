package de.structuremade.ms.sicksercvice.utils.database.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Getter
@Setter
public class Sick {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(name = "dateOfSickness")
    private Date dateOfSickness;

    @Column
    private boolean apologized = false;

    @Column
    private String commentary;

    @ManyToOne(targetEntity =  User.class)
    @JoinColumn(name = "student")
    private User user;
}
