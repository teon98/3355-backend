package com.samsam.vo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="profiles")
@Entity
@Data
public class ProfileVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="PROFILES_SEQUENCE_GENERATOR", sequenceName = "PROFILES_SEQUENCE", initialValue =100, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "PROFILES_SEQUENCE_GENERATOR")
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer profileId;
	
	@Column(nullable = true)
	private String profileLevel;
	@Column(length = 1000)
	private String profileImg;
	@Column(length = 450)
	private String profileAbout;  
	 
	@OneToOne(cascade = CascadeType.ALL) 
	@JoinColumn(name="user_no")
	UserVO user;
	 
}