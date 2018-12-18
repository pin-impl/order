package com.icl.iclorder.domain;

import com.icl.iclorder.domain.enums.OrderStatus;
import com.icl.iclorder.domain.enums.OrderType;
import com.icl.iclorder.domain.enums.PayStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class OrderEntry extends BaseEntity {
    private String name;
    private OrderType type;
    private OrderStatus status;
    private PayStatus payStatus;
    private BigDecimal totalPrice;
    private String currency;
    private String buyer;
    private String hospital;
    private String salesman;
    private String customer;
    private String customerPhone;
    private String department;
    private String receiver;
    private Boolean deleted;
    private String createBy;
}
