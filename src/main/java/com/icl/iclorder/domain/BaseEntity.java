package com.icl.iclorder.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@ToString
public class BaseEntity implements Serializable {
    @Id
    private Long id;
    private Date createTime;
    private Date updateTime;

    @Transient
    private Integer page = 1;
    @Transient
    private Integer size = 10;
}
