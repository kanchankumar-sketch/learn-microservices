package in.reinventing.inventoryservice;

import in.reinventing.inventoryservice.model.Inventory;
import in.reinventing.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {
    @Autowired
    private InventoryRepository inventoryRepository;

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData() {
        return args-> {
            Inventory in=new Inventory();
            in.setId(1L);
            in.setSkuCode("iphone_13");
            in.setQuantity(100);
            Inventory in1=new Inventory();
            in1.setId(2L);
            in1.setSkuCode("iphone_13_red");
            in1.setQuantity(0);
            this.inventoryRepository.save(in);
            this.inventoryRepository.save(in1);
        };
    }

}
