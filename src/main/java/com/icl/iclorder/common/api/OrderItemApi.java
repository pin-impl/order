package com.icl.iclorder.common.api;

import com.icl.iclorder.domain.OrderItem;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/order/item")
public interface OrderItemApi {

    @GetMapping("list")
    List<OrderItem> listOrderItem(OrderItem orderItem);

    @GetMapping("count")
    int countOrderItem(OrderItem orderItem);

    @PutMapping("add")
    OrderItem addOrderItem(@RequestBody OrderItem orderItem);

    @DeleteMapping("{id}/delete")
    Boolean dropOrderItem(@PathVariable("id") Long id);

    @PostMapping("update")
    OrderItem updateOrderItem(@RequestBody OrderItem orderItem);
}
