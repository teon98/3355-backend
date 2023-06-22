package com.samsam.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "alarms")
@Entity
@Getter
@Setter
@ToString(exclude = "user")
public class AlarmVO {

	@Id
	@SequenceGenerator(name = "ALARMS_SEQUENCE_GENERATOR", sequenceName = "ALARMS_SEQUENCE", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ALARMS_SEQUENCE_GENERATOR")
	private Integer alarmNo;

	@Column(nullable = false)
	private String alarmCategory;
	@Column(nullable = false)
	private String alarmMsg;

	@Column(nullable = false)
	private String alarmDate;
	@Column(nullable = false)
	@Builder.Default
	private Boolean alarmStatus = false;

	// 연관관계 설정n:1
	@ManyToOne
	@JoinColumn(name = "user_no")
	@JsonIgnore
	UserVO user;

}
