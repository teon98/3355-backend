package com.samsam.vo;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionVO {
	private Integer amount;
	private Integer amountHistory;
    private Timestamp date;
    private String storeName;
    private String type;
}
