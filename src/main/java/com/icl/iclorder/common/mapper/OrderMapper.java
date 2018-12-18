package com.icl.iclorder.common.mapper;

import com.icl.iclorder.domain.OrderEntry;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<OrderEntry> {
}
