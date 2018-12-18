package com.icl.iclorder.common.api;

import com.icl.iclorder.domain.OrderEntry;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/order")
public interface OrderApi {

    @GetMapping("list")
    List<OrderEntry> listOrder(OrderEntry order);

    @GetMapping("count")
    int countOrder(OrderEntry order);

    @PostMapping("update")
    OrderEntry updateOrder(@RequestBody OrderEntry order);

    @DeleteMapping("{id}/delete")
    Boolean dropOrder(@PathVariable("id") long id);

    @PutMapping("add")
    OrderEntry addOrder(@RequestBody OrderEntry order);
}
