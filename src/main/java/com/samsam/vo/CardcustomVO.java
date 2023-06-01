package com.samsam.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cardcustoms")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardcustomVO {
	
	@Id
	@SequenceGenerator(name="CARDCUSTOMS_SEQUENCE_GENERATOR", sequenceName = "CARDCUSTOMS_SEQUENCE", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "CARDCUSTOMS_SEQUENCE_GENERATOR")
	private Integer customNo;
	
	@Column(nullable = false)
	@Builder.Default
	private String customColor = "gray";
	
	@Column(nullable = true, length = 30)
	@Builder.Default
	private String customLettering = "";
	
	@ManyToOne
	private CardVO card;

}
