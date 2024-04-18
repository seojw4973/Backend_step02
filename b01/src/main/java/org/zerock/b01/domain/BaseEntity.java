package org.zerock.b01.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/* Board 테이블이 아닌 다른 테이블을 여러개 만들 때
    상속을 함으로써 공통 컬럼을 추가
* */

/* 해당 항목에 대한 조건이 감지되었을 때 이벤트를 받아서 처리
   @SpringBootApplication을 설정한 main함수 포함 클래스에
   @EnableJpaAuditing를 설정해주면 내부적으로 이벤트를 받아서
   자동으로 처리한다.
* */

@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
@Getter
abstract class BaseEntity {

    // 데이터가 새로 생성되었을 때 시간 정보 저장
    // 수정할 때는 갱신하지 마라
    @CreatedDate
    @Column(name="regdate", updatable = false)
    private LocalDateTime regDate;

    // 마지막 수정시간을 저장하라
    // 수정할 때 마다
    @LastModifiedDate
    @Column(name="moddate")
    private LocalDateTime modDate;
}
