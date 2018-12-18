package com.icl.iclorder.order;

import com.icl.iclorder.common.api.OrderApi;
import com.icl.iclorder.domain.OrderEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class OrderController implements OrderApi {

    @Autowired
    private OrderService orderService;

    public List<OrderEntry> listOrder(OrderEntry order) {
        log.info("list order param: {}", order.toString());
        return orderService.listOrder(order);
    }

    public int countOrder(OrderEntry order) {
        log.info("count order param: {}", order.toString());
        return orderService.countOrder(order);
    }

    public OrderEntry updateOrder(OrderEntry order) {
        log.info("update order param: {}", order.toString());
        return orderService.updateOrder(order);
    }

    public Boolean dropOrder(long id) {
        log.info("delete order by id: {}", id);
        return orderService.dropOrder(id);
    }

    public OrderEntry addOrder(OrderEntry order) {
        log.info("add order. param: {}", order.toString());
        return orderService.addOrder(order);
    }
}
