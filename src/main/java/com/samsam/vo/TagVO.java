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

//태영
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tags")
@Entity
@Data
public class TagVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "HASHTAG_SEQUENCE_GENERATOR", sequenceName = "HASHTAG_SEQUENCE", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HASHTAG_SEQUENCE_GENERATOR")
	private Integer tagNo;
	@Column(unique = true)
	private String tagContent;
	@Column
	private Integer tagCount;

}
