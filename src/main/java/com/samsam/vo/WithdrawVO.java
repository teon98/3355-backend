package com.samsam.vo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "withdraws")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawVO {

	@Id
	@SequenceGenerator(name = "WITHDRAWS_SEQUENCE_GENERATOR", sequenceName = "WITHDRAWS_SEQUENCE", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WITHDRAWS_SEQUENCE_GENERATOR")
	private Integer withdrawNo;

	@Column(nullable = true)
	private Integer withdrawCash;

	@Column(nullable = false)
	private Integer withdrawHistory;

	@Column(nullable = true)
	private Integer withdrawPoint;

	@Column(nullable = false)
	@CreationTimestamp
	private Timestamp withdrawDate;

	@ManyToOne
	private CardVO card;

	@ManyToOne
	private StoreVO store;

}
