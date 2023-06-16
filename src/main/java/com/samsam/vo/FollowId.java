package com.samsam.vo;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowId implements Serializable {// 복합키 생성
	private static final long serialVersionUID = 1L;

	// 두개의 user_no를 받아와 복합키 생성
	@ManyToOne
	@JoinColumn(name = "follow_start", nullable = false)
	private UserVO followStart;

	@ManyToOne
	@JoinColumn(name = "follow_end", nullable = false)
	private UserVO followEnd;

}
