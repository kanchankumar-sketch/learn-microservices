package in.reinventing.orderservice.service;

import in.reinventing.orderservice.dto.InventoryResponse;
import in.reinventing.orderservice.dto.OrderLineItemDTO;
import in.reinventing.orderservice.dto.OrderRequest;
import in.reinventing.orderservice.entity.Order;
import in.reinventing.orderservice.entity.OrderLineItem;
import in.reinventing.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private WebClient.Builder webClient;

	public void placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		List<OrderLineItem> orders = orderRequest.getOrderLineItemDTOS().stream().map(this::mapToDTO).toList();
		order.setOrderLineItemList(orders);
		
		List<String> skuCodes=order.getOrderLineItemList().stream().map(orderlimeitem->orderlimeitem.getSkuCode()).toList();
		
		InventoryResponse[] inventoryResponse=this.webClient.build().get()
		.uri("http://INVENTORY-SERVICE/api/inventory",uriBuilder->uriBuilder.queryParam("skuCode", skuCodes).build())
		.retrieve()
		.bodyToMono(InventoryResponse[].class)
		.block();
		
		boolean allProductInstock=Arrays.stream(inventoryResponse).allMatch(InventoryResponse::getInStock);
		
		if(allProductInstock) {
		this.orderRepository.save(order);
		}else {
			throw new IllegalArgumentException("Producti is not in stock, Please try again later.");
		}
	}

	private OrderLineItem mapToDTO(OrderLineItemDTO orderLineItemDTO) {
		OrderLineItem item = new OrderLineItem();
		item.setPrice(orderLineItemDTO.getPrice());
		item.setQuantity(orderLineItemDTO.getQuantity());
		item.setSkuCode(orderLineItemDTO.getSkuCode());
		return item;
	}
}
