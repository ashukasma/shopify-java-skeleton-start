package com.lucent.skeleton.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "store_details")
@Data
public class StoreDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "shopify_store_id")
	@Setter(AccessLevel.PUBLIC)
	@Getter(AccessLevel.PRIVATE)
	private Long shopifyStoreId;

	@Column(name = "my_shopify_url")
	private String myShopfiyUrl;

	@Column(name = "store_url")
	private String storeUrl;

	@Column(name = "name")
	private String name;

	@Column(name = "owner_name")
	private String ownerName;

	@Column(name = "plan")
	private String plan;

	@Column(name = "phone_no")
	private Long phoneNo;

	@Column(name = "email")
	private String email;

	@Column(name = "plan_renewal_date")
	private Date planRenewalDate;

	@Column(name = "currency")
	private String currency;

	@Column(name = "store_token")
	private String accessToken;

	@Column(name="current_status", columnDefinition = "boolean default false")
	private boolean currentBillingStatus;

	@Column(name="timezone")
	private String timeZone;

	@Column(name="intro_tour", columnDefinition = "integer default 0")
	private Integer introTour;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updatedAt;

}
