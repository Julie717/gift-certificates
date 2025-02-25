package com.project.sa.model;

import com.project.sa.dao.ColumnName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.PrePersist;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
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
@Table(name = "purchase")
public class Purchase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ColumnName.PURCHASE_ID)
    Long id;

    @Column(name = ColumnName.PURCHASE_COST)
    BigDecimal cost;

    @Column(name = ColumnName.PURCHASE_DATE)
    Timestamp purchaseDate;

    @ManyToOne
    @JoinColumn(name = ColumnName.USER_ID)
    User user;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "purchase_gift_certificate",
            joinColumns = {@JoinColumn(name = ColumnName.PURCHASE_ID)},
            inverseJoinColumns = {@JoinColumn(name = ColumnName.GIFT_CERTIFICATE_ID)})
    List<GiftCertificate> giftCertificates;

    @PrePersist
    public void onPrePersist() {
        Timestamp date = Timestamp.valueOf(LocalDateTime.now());
        setPurchaseDate(date);
    }
}