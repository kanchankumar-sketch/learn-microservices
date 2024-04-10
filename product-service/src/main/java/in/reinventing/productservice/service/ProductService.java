package in.reinventing.productservice.service;

import in.reinventing.productservice.dto.ProductRequest;
import in.reinventing.productservice.dto.ProductResponse;
import in.reinventing.productservice.model.Product;
import in.reinventing.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest){
        Product product=Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        this.productRepository.save(product);
        log.info("Product {} created ",product.getId());
    }

    public List<ProductResponse> getProducts(){
        List<Product> products = this.productRepository.findAll();
        return products.stream().map(this::getProductResponse).collect(Collectors.toList());
    }
    private ProductResponse getProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
