package com.selectshop.shop.utils;

import com.selectshop.shop.models.ItemDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component // NaverShopSearch를 컴포넌트 등록(서버가 실행되면 컴포넌트스캔에 의해서 빈으로 등록되게 하기위해)
public class NaverShopSearch { // ** 네이버 검색 api를 이용해서, 키워드로 상품을 검색하고 그 결과를 얻기위한 코드. ARC에서 요청하는 작업을 자바 코드로 표현한것임.
    public String search(String query) { // ** 검색어를 사용자가 검색하는것에따라 바꿀수있어여함.사용자가 입력한 문자열에대해 검색하는 메소드
                                         // ** 검색어를 사용자가 검색하는것에따라 바꿀수있어야 하기위해 search메소드의 파라미터에 값을 넣어줌
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "rnq6NIrFCFgHTDHsAgN9");
        headers.add("X-Naver-Client-Secret", "jcl0cj3MUF");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity
                = rest.exchange("https://openapi.naver.com/v1/search/shop.json?query=" + query, HttpMethod.GET, requestEntity, String.class);
                    // ** 검색어를 사용자가 검색하는것에따라 바꿀수있어야 하기위해 search메소드에서 전달받은 파라미터를 대입
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        //System.out.println("Response status: " + status);
        //System.out.println(response);

        return response;
    }

    public List<ItemDto> fromJSONtoItems(String result){ //사용자가 입력한 상품의 검색결과를 반환하기 위한 메소드
        JSONObject rjson = new JSONObject(result); //검색결과(search메소드의 리턴값)인 result(JSON문자열)을 JSONObject(JSON객체)로 변환
        System.out.println(rjson);
        // JSON객체인 rjson에는 items라는 키로, 값에는 검색결과가 리스트형태(JSON 형태의 배열인 [{.....},{...}] )로 들어있다.
        JSONArray items = rjson.getJSONArray("items"); // rjson에서 JSONArray 꺼내기

        List<ItemDto> itemDtoList = new ArrayList<>(); //상품의 검색결과가 1개가 아닌 여러개가 나와야하므로 List생성

        for (int i=0; i<items.length(); i++) {
            JSONObject itemJson = items.getJSONObject(i); //여러개의 JSONObject로 되어있는 JSONArray에서 JSONObject(ex.{"key":"value"}) 하나씩 꺼내기
            ItemDto itemDto = new ItemDto(itemJson); // JSON형태의 ItemDto생성


            //상품의 검색결과가 1개가 아닌 여러개가 나와야하므로 배열(리스트)에 ItemDto를 넣어주고 이 List를 반환할것임
            itemDtoList.add(itemDto);

            //JSONObject(JSON객체)에서 필요한 정보인 이미지의 주소, 링크(상품의 실제 네이버주소), 상품의 제목, 가격 등을 꺼내야함
            /*String title = itemJson.getString("title");//JSONObject에서 키에 해당하는 값을 가져오기. 값은 문자열이고 문자열인 값을 꺼내려면 getString()으로.
            String image = itemJson.getString("image");
            int lprice = itemJson.getInt("lprice");
            String link = itemJson.getString("link");*/
        }
        return itemDtoList;

    }

    /*public static void main(String[] args) {
        NaverShopSearch naverShopSearch = new NaverShopSearch();
        String result = naverShopSearch.search("아이맥");
        System.out.println(naverShopSearch.fromJSONtoItems(result));
    }*/
}
