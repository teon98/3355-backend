package com.samsam.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
@Entity
@Data 
public class UserVO implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="USERS_SEQUENCE_GENERATOR", sequenceName = "USERS_SEQUENCE", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "USERS_SEQUENCE_GENERATOR")
	private Integer userNo;
	
	
	@Column(nullable = false, unique = true)
	private String userNickname;
	@Column(nullable = false, unique = true)
	private String userEmail;
	@Column(nullable = false)
	private String userPass;
	@Column(nullable = false)
	private Integer userBirth;
	@Column(nullable = false)
	private Integer userGender;
} 
	 
