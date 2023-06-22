package com.samsam.vo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "users")
@Entity
@Getter
@Setter
public class UserVO implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "USERS_SEQUENCE_GENERATOR", sequenceName = "USERS_SEQUENCE", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQUENCE_GENERATOR")
	private Integer userNo;

	
	
	@Column(nullable = true, unique = true)
	private String userNickname;
	@Column(nullable = true, unique = true)
	private String userEmail;

	@Column(nullable = true)
	private String userPass; 
	@Column(nullable = true)
	private Integer userBirth;
	@Column(nullable = true)
	private Integer userGender;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<WorkVO> work;
	@JsonIgnore
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private ProfileVO profile;
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<DailyStampVO> dailyStamp;
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<PostVO> post;
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<CardcustomVO> custom;
}
