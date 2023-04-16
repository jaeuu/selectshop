package com.selectshop.shop.models;

import org.springframework.data.jpa.repository.JpaRepository;

// (** JpaRepository< > 안에는 각각 엔티티 클래스 이름과 ID 필드 타입이 지정된다.
// (**JpaRepository를 상속함으로써 JpaRepository에 작성된 메서드를 갖다 쓸수있다. findAll(), delete(), findByID(), save() 등등.
public interface ProductRepository extends JpaRepository<Product, Long> {
}
