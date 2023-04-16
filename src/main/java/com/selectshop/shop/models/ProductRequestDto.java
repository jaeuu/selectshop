package com.selectshop.shop.models;


import lombok.Getter;

// ** 등록할 관심상품의 데이터를 가지고 있는 객체)
@Getter //(** private으로 선언된 필드를 가져오기 위한 Getter)
public class ProductRequestDto { // ** 관심상품등록할떄 필요한 dto . 상품제목, 링크(링크를 누르면 상세상품화면으로 가야하므로), 이미지, 최저가 가격이 필요함
    private String title;
    private String link;
    private String image;
    private int lprice;
}
