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
@Table(name = "dailystamps")
@Entity
@Data
public class DailyStampVO {// 출석체크 브이오

	@Id
	@SequenceGenerator(name = "DAILYS_SEQUENCE_GENERATOR", sequenceName = "DAILYS_SEQUENCE", initialValue = 600, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DAILYS_SEQUENCE_GENERATOR")
	private Integer dailyNo;

	@CreationTimestamp
	private Timestamp dailyDate;

	// 연관관계 설정n:1
	// FK: 칼람이 board_bnofh 로 생성된다
	@ManyToOne
	@JoinColumn(name = "user_no")
	UserVO user;
}
