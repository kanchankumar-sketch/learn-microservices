package in.reinventing.inventoryservice.controller;

import in.reinventing.inventoryservice.dto.InventoryResponse;
import in.reinventing.inventoryservice.service.InventoryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService  inventoryService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> inStock(@RequestParam("skuCode") List<String> skuCodes){
        return this.inventoryService.inStock(skuCodes);
    }
}
