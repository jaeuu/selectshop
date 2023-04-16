package com.selectshop.shop.service;

import com.selectshop.shop.models.ItemDto;
import com.selectshop.shop.models.Product;
import com.selectshop.shop.models.ProductMypriceRequestDto;
import com.selectshop.shop.models.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

    @RequiredArgsConstructor //(** final로 선언된 필드에 대한 객체를 생성해서 자동주입해줌)
    @Service //(** 스프링한테 MemoService가 Service임을 알려주기 위해 적어줌)
    public class ProductService { // (** 생성,조회,변경,삭제 중에 변경 기능을 할때 Service가 필요함)
        //(** 스프링이 ProductRepository를 자동주입 해주려면 final로 해주고 @RequiredArgsConstructor를 해줘야함)
        private final ProductRepository productRepository; //(** Product를 find해서 찾으려면 ProductRepository에서 찾아야하므로)

        @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다. (**update를 할때 DB에 반영이 되야한다고 알려주는 어노테이션)
        public Long update(Long id, ProductMypriceRequestDto requestDto) { //(**변경시킬 관심상품의 id, 변경시킬 관심상품의 관심가격정보를 파라미터로 받음)
            // 관심가격정보가 전달되고 id를 받아서 그 관심상품의 관심가격을 변경하는 메소드
            Product product = productRepository.findById(id).orElseThrow( //(**findById를 하는데 없을 경우를 대비해서 orElseThrow를 한다)
                    () -> new NullPointerException("해당 아이디가 존재하지 않습니다.") //(** 예외 및 예외 메시지를 보여줌)
            );
            product.update(requestDto); //(** 찾아온 Product를 변경)
            return id; //(** 어떤 Product가 변경됬는지 변경된 Product의 id를 리턴)
        }

        @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다. (**update를 할때 DB에 반영이 되야한다고 알려주는 어노테이션)
        public Long updateBySearch(Long id, ItemDto itemDto) { // ** 변경시킬 관심상품의 id, 변경시킬 상품의정보를 파라미터로 받음
            Product product = productRepository.findById(id).orElseThrow(
                    () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
            );
            product.updateByItemDto(itemDto); // **itemDto(검색상품)를 받아서 관심상품의 정보를 업데이트
            return id; //(** 어떤 Product가 변경됬는지 변경된 Product의 id를 리턴)
    }
}
