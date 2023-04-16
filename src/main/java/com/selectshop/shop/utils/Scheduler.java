package com.selectshop.shop.utils;

import com.selectshop.shop.models.ItemDto;
import com.selectshop.shop.models.Product;
import com.selectshop.shop.models.ProductRepository;
import com.selectshop.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor // final 멤버 변수를 자동으로 생성합니다.
@Component // 스프링이 필요 시 자동으로 빈으로 생성하는 클래스
public class Scheduler { // **최저가 정보를 업데이트하는 스케줄러

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final NaverShopSearch naverShopSearch;


    // 매일 새벽 1시에 관심상품 목록 제목으로 검색해서, 최저가 정보를 업데이트하는 스케줄러
    // **  cron의 값은 초, 분, 시, 일, 월, 주 순서이다.
    // **  *은 무슨값이든지 상관없다는것이다. 시간은 0~23까지의 값이다.
    @Scheduled(cron = "0 0 1 * * *") //** 정해진 시간(am 01:00)에 메소드를 실행하는 어노테이션
    public void updatePrice() throws InterruptedException { // ** 에러가 발생했을때 발생하는 예외
        System.out.println("가격 업데이트 실행");
        // 저장된 모든 관심상품을 조회합니다.
        List<Product> productList = productRepository.findAll();
        for (int i=0; i<productList.size(); i++) {
            // 1초에 한 상품 씩 조회합니다 (Naver에서 1초에 한번씩 조회하도록 제한)
            TimeUnit.SECONDS.sleep(1); // ** 1초동안 일시정지. 1초에 한 상품씩 조회하기위해

            // i 번째 관심 상품을 꺼냅니다.
            Product p = productList.get(i);

            // i 번째 관심 상품의 제목으로 검색을 실행합니다.
            String title = p.getTitle();

            String resultString = naverShopSearch.search(title);
            // i 번째 관심 상품의 검색 결과 목록 중에서 첫 번째 결과를 꺼냅니다.
            List<ItemDto> itemDtoList = naverShopSearch.fromJSONtoItems(resultString);

            ItemDto itemDto = itemDtoList.get(0); // ** 제목으로 검색된 여러가지 ItemDto중에서 0번째에 있는것이 검색한 상품과 제일 유사한 상품이므로

            // i 번째 관심 상품 정보를 업데이트합니다.
            Long id = p.getId();
            productService.updateBySearch(id, itemDto);
        }
    }
}
