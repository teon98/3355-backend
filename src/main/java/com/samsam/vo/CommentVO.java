package com.samsam.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//태영
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
@Entity
@Data
public class CommentVO implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "COMM_SEQUENCE_GENERATOR", sequenceName = "COMM_SEQUENCE", initialValue = 500, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMM_SEQUENCE_GENERATOR")
	private Integer commNo;
	@Column(length = 300)
	private String commContent;
	@UpdateTimestamp
	private Timestamp commDate;

	@ManyToOne
	@JoinColumn(name = "post_no")
	@JsonIgnore
	private PostVO post;

	@ManyToOne
	@JoinColumn(name = "user_no")
	@JsonIgnore
	private UserVO commuser;
}
