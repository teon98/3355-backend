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
@Table(name = "points")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointVO {
	@Id
	@SequenceGenerator(name = "POINTS_SEQUENCE_GENERATOR", sequenceName = "POINTS_SEQUENCE", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POINTS_SEQUENCE_GENERATOR")
	private Integer pointNo;
	
	@Column(nullable = true)
	private Integer withdrawNo;

	@Column(nullable = false)
	private Integer pointSave;

	@Column(nullable = false)
	private String pointMemo;

	@Column(nullable = false)
	private Integer pointHistory;

	@Column(nullable = false)
	@CreationTimestamp
	private Timestamp pointDate;

	@ManyToOne
	private CardVO card;
}
