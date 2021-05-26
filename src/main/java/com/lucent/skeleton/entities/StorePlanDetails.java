package com.lucent.skeleton.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "store_plan_details")
public class StorePlanDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id", nullable = false, referencedColumnName = "id")
    private StoreDetails store;

    @Column(name = "max_charge")
    private Double maxCharge;

    @Column(name = "current_charge")
    private Double currentCharge;

    @Column(name = "discount")
    private Double discount;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public static StorePlanDetails build(StoreDetails storeDetails, Double max_charge ) {
        StorePlanDetails storePlanDetails = new StorePlanDetails();
        storePlanDetails.setStore(storeDetails);
        storePlanDetails.setDiscount(Double.valueOf(0));
        storePlanDetails.setCurrentCharge(Double.valueOf(0));
        storePlanDetails.setMaxCharge(max_charge);
        return storePlanDetails;
    }
}
