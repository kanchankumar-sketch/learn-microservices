package in.reinventing.inventoryservice.service;

import in.reinventing.inventoryservice.dto.InventoryResponse;
import in.reinventing.inventoryservice.model.Inventory;
import in.reinventing.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public List<InventoryResponse> inStock(List<String> skuCodes){
       return this.inventoryRepository.findBySkuCodeIn(skuCodes)
    		   .stream()
    		   .map(inventor->
    			   InventoryResponse.builder()
    			   .skuCode(inventor.getSkuCode())
    			   .inStock(inventor.getQuantity()>0)
    			   .build()
    		   ).toList();
    }

}
