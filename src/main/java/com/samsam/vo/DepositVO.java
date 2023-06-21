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
@Table(name = "deposits")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositVO {
	@Id
	@SequenceGenerator(name = "DEPOSITS_SEQUENCE_GENERATOR", sequenceName = "DEPOSITS_SEQUENCE", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEPOSITS_SEQUENCE_GENERATOR")
	private Integer depositNo;

	@Column(nullable = false)
	private Integer depositCash;

	@Column(nullable = false)
	private Integer depositHistory;

	@Column(nullable = false)
	@CreationTimestamp
	private Timestamp depositDate;

	@ManyToOne
	private CardVO card;
}
