package com.project.sa.model;

import com.project.sa.dao.ColumnName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ColumnName.USER_ID)
    Long id;

    @Column(name = ColumnName.USER_NAME, length = 20)
    String name;

    @Column(name = ColumnName.USER_SURNAME, length = 50)
    String surname;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    List<Purchase> purchases;
}