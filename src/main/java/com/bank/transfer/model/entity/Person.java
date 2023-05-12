package com.bank.transfer.model.entity;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@SequenceGenerator(name = "sequence", sequenceName = "MY_CUSTOM_SEQ", allocationSize=1)
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column (nullable = false)
    private String name;

    @Column (nullable = false, name = "DATE_OF_BIRTH")
    private LocalDate birthday;


    @Column (nullable = false)
    @Size(min = 8, max = 400)
    private String password;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
