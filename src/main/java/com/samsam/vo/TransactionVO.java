package com.samsam.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class TransactionVO {
	private Integer no;
	private String type;
	private Integer amount;
	private Integer amountHistory;
	private String date;
	private String storeName;
}
