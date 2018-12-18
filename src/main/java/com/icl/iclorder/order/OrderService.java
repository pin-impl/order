package com.icl.iclorder.order;

import com.icl.iclorder.common.mapper.OrderMapper;
import com.icl.iclorder.domain.OrderEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OrderService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private OrderMapper orderMapper;

    public List<OrderEntry> listOrder(OrderEntry order) {
        order.setDeleted(false);
        return orderMapper.select(order);
    }

    public int countOrder(OrderEntry order) {
        order.setDeleted(false);
        return orderMapper.selectCount(order);
    }

    public OrderEntry updateOrder(OrderEntry order) {
        order.setUpdateTime(new Date());
        orderMapper.updateByPrimaryKeySelective(order);
        return orderMapper.selectByPrimaryKey(order.getId());
    }

    public Boolean dropOrder(long id) {
        OrderEntry order = new OrderEntry();
        order.setDeleted(true).setId(id);
        return orderMapper.updateByPrimaryKeySelective(order) == 1;
    }

    public OrderEntry addOrder(OrderEntry order) {
        order.setDeleted(false).setCreateTime(new Date()).setUpdateTime(new Date());
        orderMapper.insertUseGeneratedKeys(order);
        return order;
    }
}
