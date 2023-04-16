package com.selectshop.shop.controller;

import com.selectshop.shop.models.Product;
import com.selectshop.shop.models.ProductMypriceRequestDto;
import com.selectshop.shop.models.ProductRepository;
import com.selectshop.shop.models.ProductRequestDto;
import com.selectshop.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor //(** final로 선언된 필드에 대한 객체를 생성해서 자동주입해줌)
@RestController // JSON으로 데이터를 주고받음을 선언합니다. (** @Controller만 있으면 스프링이 알아서 ProductController빈을 생성해줌
public class ProductRestController { //(** @RestController는 @Controller와 @ResponseBody가 결합한 어노테이션이다 그래서 메서드에 @ResponseBody를 안적어도됨)

    private final ProductRepository productRepository; //(**CRUD중 생성,조회,삭제를 위해 Repository가 필요함)
    private final ProductService productService;

    // 등록된 전체 상품 목록 조회
    @GetMapping("/api/products")
    public List<Product> getProducts() { // ** 관심상품의 목록을 반환하는 메소드. 관심상품의 목록을 반환하므로 List로
        return productRepository.findAll(); // ** ProductRepository에 있는 엔티티(Product) 목록을 리턴한다.
    }

    @PostMapping("/api/products") //(**클라이언트가 요청할때 보낸 json 데이터를 ProductRequestDto객체에 바로 넣으려면 @RequestBody가 필요하다)
    public Product createProduct(@RequestBody ProductRequestDto requestDto){ // ** 관심상품을 등록하는 메소드. 생성할 관심상품의 데이터가 필요하고, 이 데이터는 Dto에 저장되어있음)
        Product product = new Product(requestDto); //(**RequestDto를 바탕으로 Product 생성)
        return productRepository.save(product); //(**ProductRepository에 Product 저장)
                                                //(**저장한 Product를 리턴)
    }

    @PutMapping("/api/products/{id}")
    public Long updateProduct(@PathVariable Long id,
                              @RequestBody ProductMypriceRequestDto requestDto) {
        return productService.update(id, requestDto);
    }

}
