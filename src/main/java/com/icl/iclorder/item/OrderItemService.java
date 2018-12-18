package com.icl.iclorder.item;

import com.icl.iclorder.common.mapper.OrderItemMapper;
import com.icl.iclorder.domain.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderItemService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private OrderItemMapper orderItemMapper;

    public List<OrderItem> listOrderItem(OrderItem item) {
        item.setDeleted(false);
        return orderItemMapper.select(item);
    }

    public int countOrderItem(OrderItem orderItem) {
        orderItem.setDeleted(false);
        return orderItemMapper.selectCount(orderItem);
    }

    public OrderItem addOrderItem(OrderItem item) {
        item.setDeleted(false).setCreateTime(new Date()).setUpdateTime(new Date());
        orderItemMapper.insertUseGeneratedKeys(item);
        return item;
    }

    public Boolean dropOrderItem(long id) {
        OrderItem item = new OrderItem();
        item.setDeleted(true).setId(id);
        return orderItemMapper.updateByPrimaryKeySelective(item) == 1;
    }

    public OrderItem updateOrderItem(OrderItem orderItem) {
        orderItem.setUpdateTime(new Date());
        orderItemMapper.updateByPrimaryKeySelective(orderItem);
        return orderItemMapper.selectByPrimaryKey(orderItem.getId());
    }
}
