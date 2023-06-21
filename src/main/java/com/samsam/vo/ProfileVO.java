package com.samsam.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "profiles")
@Entity
@Getter
@Setter
@ToString(exclude = "user")
public class ProfileVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PROFILES_SEQUENCE_GENERATOR", sequenceName = "PROFILES_SEQUENCE", initialValue = 100, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROFILES_SEQUENCE_GENERATOR")
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer profileId;

	@Enumerated(EnumType.STRING) // 열거형 타입중 문자만 저장
	@Builder.Default
	private UserLevelRole profileLevel = UserLevelRole.BRONZE1;
	@Column(length = 1000)
	private String profileImg;
	@Column(length = 450)
	private String profileAbout;

	@OneToOne
	@JoinColumn(name = "user_no")
	@JsonIgnore
	UserVO user;

}