package com.selectshop.shop.models;

import lombok.Getter;
import org.json.JSONObject;


@Getter //외부에서 private한 멤버변수를 꺼내기위해
public class ItemDto { // 사용자가 검색한 결과를 돌려주려면 데이터를 가지고있는 클래스인 ItemDto가 필요하다.
    private String title;
    private String link;
    private String image;
    private int lprice;

    public ItemDto(JSONObject itemJson) { // 생성자를 통해 JSONObject 객체 주입
        this.title = itemJson.getString("title");
        this.link = itemJson.getString("link");
        this.image = itemJson.getString("image");
        this.lprice = itemJson.getInt("lprice");
    }
}
