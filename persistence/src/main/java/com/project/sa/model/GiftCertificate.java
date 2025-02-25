package com.project.sa.model;

import com.project.sa.dao.ColumnName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "gift_certificate")
public class GiftCertificate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ColumnName.GIFT_CERTIFICATE_ID)
    Long id;

    @Column(name = ColumnName.GIFT_CERTIFICATE_NAME,length = 45)
    String name;

    @Column(name = ColumnName.GIFT_CERTIFICATE_DESCRIPTION, length = 1000)
    String description;

    @Column(name = ColumnName.GIFT_CERTIFICATE_PRICE)
    BigDecimal price;

    @Column(name = ColumnName.GIFT_CERTIFICATE_DURATION)
    Integer duration;

    @Column(name = ColumnName.GIFT_CERTIFICATE_CREATE_DATE)
    Timestamp createDate;

    @Column(name = ColumnName.GIFT_CERTIFICATE_LAST_UPDATE_DATE)
    Timestamp lastUpdateDate;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "gift_certificate_tag",
            joinColumns = {@JoinColumn(name = ColumnName.GIFT_CERTIFICATE_ID)},
            inverseJoinColumns = {@JoinColumn(name = ColumnName.TAG_ID)})
    List<Tag> tags;

    @PrePersist
    public void onPrePersist() {
        Timestamp date = Timestamp.valueOf(LocalDateTime.now());
        setCreateDate(date);
        setLastUpdateDate(date);
    }

    @PreUpdate
    public void onPreUpdate() {
        setLastUpdateDate(Timestamp.valueOf(LocalDateTime.now()));
    }
}