package com.selectshop.shop.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // ** get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // ** 기본 생성자를 만들어줍니다.
@Entity // ** DB 테이블 역할을 합니다.
public class Product extends Timestamped{ //** 관심상품 역할을 하는 Product. 생성,수정 시간을 자동으로 만들어줍니다.

    // ** ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id; // (** Memo클래스는 @Entity를 적어서 테이블임을 나타내므로 Memo클래스의 필드는 테이블의 컬럼이 된다)

    // 반드시 값을 가지도록 합니다.
    @Column(nullable = false)
    private String title; // ** 상품제목

    @Column(nullable = false)
    private String image; // ** 이미지

    @Column(nullable = false)
    private String link; // ** 링크

    @Column(nullable = false)
    private int lprice; // ** 최저가 가격

    @Column(nullable = false) //** 관심상품 가격도 무조건 있어야함. 만약 관심상품 가격 설정을 안하고 관심상품을 등록하면 최저가 딱지가 붙으면 안됨
    private int myprice; // ** 사용자가 설정한 관심가격

    // ** 등록할 관심상품의 데이터를 가지고 있는 ProductRequestDto를 바탕으로 Product를 만듬
    public Product(ProductRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.link = requestDto.getLink();
        this.lprice = requestDto.getLprice();
        this.image = requestDto.getImage();
        this.myprice = 0 ; // ** 사용자가 관심상품 가격 설정을 안하고 관심상품을 등록하면 0원으로 설정한다. 0원으로 설정하면 최저가 딱지가 붙을일이 없으므로.
    }                      // 최저가 딱지는 사용자가  설정한 관심상품 가격보다 더 낮은가격일때 노출되는것이다.

    public void update(ProductMypriceRequestDto requestDto){ // 관심상품의 관심가격을 변경하는 메소드
        this.myprice = requestDto.getMyprice();
    }

    public void updateByItemDto(ItemDto itemDto){ // 검색상품을 받아서 관심상품의 정보를 업데이트하는 메소드
        this.lprice = itemDto.getLprice(); // 가격 정보만 다를테니 가격정보만 업데이트 하면됨
    }
}
