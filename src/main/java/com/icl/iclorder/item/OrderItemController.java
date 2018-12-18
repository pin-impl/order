package com.icl.iclorder.item;


import com.icl.iclorder.common.api.OrderItemApi;
import com.icl.iclorder.domain.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class OrderItemController implements OrderItemApi {

    @Autowired
    private OrderItemService orderItemService;

    public List<OrderItem> listOrderItem(OrderItem orderItem) {
        log.info("list order item. param: {}", orderItem.toString());
        return orderItemService.listOrderItem(orderItem);
    }

    public int countOrderItem(OrderItem orderItem) {
        log.info("count order item. param: {}", orderItem.toString());
        return orderItemService.countOrderItem(orderItem);
    }

    public OrderItem addOrderItem(@RequestBody OrderItem orderItem) {
        log.info("add order item. param: {}", orderItem.toString());
        return orderItemService.addOrderItem(orderItem);
    }

    public Boolean dropOrderItem(@PathVariable("id") Long id) {
        log.info("delete order item id: {}", id);
        return orderItemService.dropOrderItem(id);
    }

    public OrderItem updateOrderItem(@RequestBody OrderItem orderItem) {
        log.info("update order item. param: {}", orderItem.toString());
        return orderItemService.updateOrderItem(orderItem);
    }

}
