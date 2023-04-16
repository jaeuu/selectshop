let targetId;

$(document).ready(function () { // ** 페이지에 처음 진입하거나 새로고침할때마다 실행하는 함수
    // id 가 query 인 녀석 위에서 엔터를 누르면 execSearch() 함수를 실행하라는 뜻입니다.
    $('#query').on('keypress', function (e) {
        if (e.key == 'Enter') {
            execSearch();
        }
    });
    $('#close').on('click', function () {
        $('#container').removeClass('active');
    })

    $('.nav div.nav-see').on('click', function () {
        $('div.nav-see').addClass('active');
        $('div.nav-search').removeClass('active');

        $('#see-area').show();
        $('#search-area').hide();
    })
    $('.nav div.nav-search').on('click', function () {
        $('div.nav-see').removeClass('active');
        $('div.nav-search').addClass('active');

        $('#see-area').hide();
        $('#search-area').show();
    })

    $('#see-area').show();
    $('#search-area').hide();

    showProduct(); // ** 관심상품 보여주는 함수
})


function numberWithCommas(x) { <!-- 숫자를 넣어주면 컴마를 위치대로 넣어준후 반환하는 메소드-->
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

//////////////////////////////////////////////////////////////////////////////////////////
///// 여기 아래에서부터 코드를 작성합니다. ////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////

function execSearch() {
    /**
     * 검색어 inputBox id: #query
     * 검색결과 목록 id: #search-result-box
     * 검색결과 HTML 만드는 함수: addHTML
     */
    $('#search-result-box').empty(); //** execSearch()메소드가 실행되면, 기존에 샘플데이터가 있는 검색결과 영역을 비워준다.

    // ** 1. 검색창의 입력값을 가져온다. jquery의 .val을 이용
    let query = $('#query').val(); // **id가 query인것의 입력값을 가져온다.
    // 2. 검색창 입력값을 검사하고, 입력하지 않았을 경우 focus.
    if(query ==''){ // ** 입력하지 않았을경우
        alert("검색어를 입력해주세요.");
        $('#query').focus // ** id가 query인것에 포커스를 하는것
    }
    // 3. GET /api/search?query=${query} 요청 / .ajax를 이용
    $.ajax({ //(** $.jax = jQuery를 이용한다는 의미임. 서버에 요청을 보낼때, ARC를 통해서 데이터를 적어서 요청한것을 코드로 하는것임)
        type: "GET", //(** GET방식으로 서버에 요청하겠다)
        url: `/api/search?query=${query}`, // ** 문자열중간에 변수를 넣으려면 백틱 및 ${}을 활용.  (**localhost:8080은 쓸 필요없다. jQuery가 알아서 다 해줌)
        success: function (response) { // (**서버가 성공적으로 응답을 한다면 서버에서 준 결과를 response라는 변수에 담는다)
            // 4. for 문마다 itemDto를 꺼내서 HTML 만들고 검색결과 목록에 붙이기!
             for (let i=0; i<response.length; i++) { // **response에는 itemDto List가 있을것이다.
                 let itemDto = response[i]; // ** itemDto하나씩 꺼냄
                 let tempHtml = addHTML(itemDto);   // ** itemDto를 HTML로 변환
                 $('#search-result-box').append(tempHtml);  // ** 선택된 id 요소의 마지막에 HTML을 추가한다.
             }
        }
    });



}

function addHTML(itemDto) { // ** itemDto를 HTML로 변환하기 위한 메소드
    /**
     * class="search-itemDto" HTML하나를 가르키는 샘플데이터에서
     * image, title, lprice, addProduct 활용하기
     * 참고) onclick='addProduct(${JSON.stringify(itemDto)})'
     */
    return `<div class="search-itemDto">
            <div class="search-itemDto-left">
                <img src="${itemDto.image}" alt=""> <!-- ** 샘플데이터에서 필요한 부분만 수정 -->
            </div>
            <div class="search-itemDto-center">
                <div>${itemDto.title}</div> <!-- ** 샘플데이터에서 필요한 부분만 수정 -->
                <div class="price">
                    ${numberWithCommas(itemDto.lprice)} <!-- ** 가격에 콤마 추가-->
                    <span class="unit">원</span>
                </div>
            </div>
            <div class="search-itemDto-right">
                <img src="images/icon-save.png" alt="" onclick='addProduct(${JSON.stringify(itemDto)})'> <!--JSON형태의 itemDto를 문자열로 변환해서 매개값으로 넣어줌 -->
                <!--이미지를 선택하면 addProduct()가 실행됨-->  
            </div>
        </div>`
}

function addProduct(itemDto) {
    /**
     * modal 뜨게 하는 법: $('#container').addClass('active');
     * data를 ajax로 전달할 때는 두 가지가 매우 중요
     * 1. contentType: "application/json",
     * 2. data: JSON.stringify(itemDto),
     */

    // 1. POST /api/products 에 관심 상품 생성 요청
    $.ajax({
        type: "POST",
        url: "/api/products",
        data: JSON.stringify(itemDto), // ** 자바스크립트에서는 JSON형태가 문자열형태인채로 매개값으로 들어와도 다시 JSON형태로 바꿔버리기때문에 다시 문자열로 바꿔줘야함
        contentType: "application/json",
        success: function (response){
            $('#container').addClass('active'); // ** modal 노출
            targetId = response.id; // ** targetId 를 reponse.id 로 설정. targetID에는 제일 최근에 담긴 관심상품의 id가 들어있다.
        }

    })
    // (숙제로 myprice 설정하기 위함)
}

function showProduct() { // ** 관심상품 보여주는 함수
    /**
     * 관심상품 목록: #product-container
     * 검색결과 목록: #search-result-box
     * 관심상품 HTML 만드는 함수: addProductItem
     */
    // 1. GET /api/products 요청
    $.ajax({
        type: 'GET',
        url: '/api/products',
        success: function (response) {
            // 2. 관심상품 목록, 검색결과 목록 비우기
            $('#product-container').empty();
            $('#search-result-box').empty();
            // 3. for 문마다 관심상품Dto을 꺼내서 HTML 만들고 관심상품 목록에 붙이기!
            for (let i=0; i<response.length; i++) { // **response에는 관심상품 List가 있을것이다.
                let product = response[i];
                let tempHtml = addProductItem(product);
                $('#product-container').append(tempHtml); // ** html을 관심상품 목록에 붙이기!
            }
        }
    })
}

function addProductItem(product) {
    // ** 관심상품 dto의 link, image, title, lprice, myprice 변수 활용하기
    return `<div class="product-card" onclick="window.location.href='${product.link}'">
            <div class="card-header">
                <img src="${product.image}"
                     alt="">
            </div>
            <div class="card-body">
                <div class="title">
                    ${product.title}
                </div>
                <div class="lprice">
                    <span>${numberWithCommas(product.lprice)}</span>원
                </div>
                <div class="isgood ${product.lprice <= product.myprice ? '' : 'none'}"> <!-- 내가설정한 가격보다 최저가가 낮으면 class = "isgood" 을 노출해서 '최저가'가 나오게하고,--> 
                    최저가                                                                 <!-- 높으면 '최저가'가 나오면 안되므로 clss = "isgood none" 으로 해서 class = "isgood" 을 미노출되게 한다. -->
                </div>                                                                  <!--css의 .none을 보면 display:none으로 되어있으므로 isgood none이되면 isgood을 미노출되게 함-->
            </div>
        </div>`;
}

function setMyprice() {
    // 1. id가 myprice 인 input 태그에서 값을 가져온다.
    let myprice = $('#myprice').val();
    // 2. 만약 값을 입력하지 않았으면 alert를 띄우고 중단한다.
    if (myprice == '') {
        alert('최저가를 입력해주세요');
        return;
    }
    // 3. PUT /api/product/${targetId} 에 data를 전달한다.
    $.ajax({
        type: 'PUT',
        url: `/api/products/${targetId}`,
        contentType: "application/json",
        data: JSON.stringify({myprice: myprice}),
        success: function (response) {
            // 4. 모달을 종료한다.
            $('#container').removeClass('active');
            // 5. 성공적으로 등록되었음을 알리는 alert를 띄운다.
            alert('성공적으로 등록되었습니다');
            // 6. 창을 새로고침한다.
            window.location.reload();
        }
    })
}