package com.selectshop.shop.models;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter //(**필드가 private이므로 필드를 조회할수 있도록 Getter를 해줌)
@MappedSuperclass // Entity(Memo)가 자동으로 컬럼으로 인식합니다.(** Timestamped 클래스를 상속한 클래스(Memo)는 자동으로 생성시간과 수정시간을 컬럼으로 만듬)
@EntityListeners(AuditingEntityListener.class) // 생성/변경 시간을 자동으로 업데이트합니다.
public abstract class Timestamped { // (** abstract를 사용한 클래스는 다른 클래스에서 상속되는 용도로만 사용가능함)

    @CreatedDate // (**생성시간을 나타냄)
    private LocalDateTime createdAt;

    @LastModifiedDate // (**마지막 수정시간을 나타냄)
    private LocalDateTime modifiedAt;
}
