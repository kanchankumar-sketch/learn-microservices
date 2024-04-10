package in.reinventing.orderservice.controller;

import in.reinventing.orderservice.dto.FallBackRespose;
import in.reinventing.orderservice.dto.OrderRequest;
import in.reinventing.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {
public final static String ORDER_SERVICE="ORDER_SERVICE";
    @Autowired
    private OrderService service;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name=ORDER_SERVICE,fallbackMethod = "OrderServciceFallBackMethod")
    public String placedOrder(@RequestBody OrderRequest order){
        this.service.placeOrder(order);
        return "success";
    }
    
    public String OrderServciceFallBackMethod(Exception e) {
//    	return FallBackRespose.builder()
//    			.message("ORDER-SERVICE POST MAPPING NOT WORKING,TRY AGAIN LATER.")
//    			.status("fail")
//    			.build();
    	return "ORDER-SERVICE POST MAPPING NOT WORKING,TRY AGAIN LATER.";
    }
}
