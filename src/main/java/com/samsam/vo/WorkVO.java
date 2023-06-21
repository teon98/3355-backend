package com.samsam.vo;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "works")
@Entity
@Data
public class WorkVO {// 오운완 브이오

	@Id
	@SequenceGenerator(name = "WORKS_SEQUENCE_GENERATOR", sequenceName = "WORKS_SEQUENCE", initialValue = 500, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WORKS_SEQUENCE_GENERATOR")
	private Integer workNo;

	@CreationTimestamp
	private Timestamp workDate;

	// 연관관계 설정n:1
	// FK: 칼람이 board_bnofh 로 생성된다
	@ManyToOne
	@JoinColumn(name = "user_no")
	UserVO user;
}
