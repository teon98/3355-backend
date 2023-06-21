package com.samsam.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "goods")
@Entity
@ToString
@Data
public class GoodVO {
	@Id
	@SequenceGenerator(name = "GOOD_SEQUENCE_GENERATOR", sequenceName = "GOOD_SEQUENCE", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GOOD_SEQUENCE_GENERATOR")
	private Integer good_no;

	@ManyToOne
	@JoinColumn(name = "user_no")
	private UserVO user;

	@ManyToOne
	@JoinColumn(name = "post_no")
	private PostVO post;
}
