package com.lucent.skeleton.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "store_scripts")
@Data
public class StoreScript {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "script_id")
    private Long scriptId;

    @Column(name = "script_tag_id")
    private String scriptTagId;

    @Column(name = "version")
    private String version;

    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

}
