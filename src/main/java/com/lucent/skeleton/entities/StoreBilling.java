package com.lucent.skeleton.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "store_billings")
@Data
public class StoreBilling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "charge_id")
    private Long chargeId;

    @Column(name = "status")
    private String status;

    @Column(name="activated_date")
    private Date activatedDate;

    @CreationTimestamp
    @Column(name="created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private Date updatedAt;
}
