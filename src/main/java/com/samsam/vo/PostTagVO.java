package com.samsam.vo;

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
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posttag")
@Entity
@ToString
@Data
public class PostTagVO {
	@Id
	@SequenceGenerator(name = "PT_SEQUENCE_GENERATOR", sequenceName = "PT_SEQUENCE", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PT_SEQUENCE_GENERATOR")
	private Integer ptNo;

	@ManyToOne
	@JoinColumn(name = "post_no")
	@JsonIgnore
	private PostVO post;

	@ManyToOne
	@JoinColumn(name = "tag_no")
	private TagVO tag;

}
