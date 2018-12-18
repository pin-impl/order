package com.icl.iclorder.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@ToString(callSuper = true)
public class OrderItem extends BaseEntity {
    private Long orderId;
    private String name;
    private String itemNo;
    private String purpose;
    private String sampleType;
    private String customerInfo;
    private BigDecimal unitPrice;
    private BigDecimal quantity;
    private String status;
    private Date testDate;
    private Boolean deleted;
}
