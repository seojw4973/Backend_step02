package org.zerock.b01.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("select r from Reply r where r.board.bno=:bno")
    Page<Reply> listOfBoard(Long bno, Pageable pageable);

    /* JPA의 DML을 사용하기 위한 3가지 방법(3가지 외에도 방법이 더 존재함)

        모두 JPQL -> Native Query -> DBMS 서버에 전달

        단순 ------------------------- 복잡
        쿼리 메서드      JPQL       Querydsl

        1) 쿼리 메서드
            기본 CRUD는 이미 자동으로 만들어져 있다.
            Repository 인터페이스에 함수명명규칙에 따라 이름을 정하면 자동으로 JPA가 JPQL로 만들어준다.

        2) JPQL
            SQL문과 거의 같다.
            이것이 나온 이유는 각 DBMS마다 약간씩 SQL이 다르므로 jpa에서 사용하는 표준을 정해놓은 것.

        3) Querydsl
            조건이 복잡할 때, 메서드 호출방식으로 개발자가 조합하기 익숙하도록 제공하는 방식
            제어문이 필요해서
            (ex. Oracle의 PL-SQL의 역할)
            
    * */

    /*
    DELETE FROM Reply r WHERE r.board_bno =:bno
    * */
    void deleteByBoard_Bno(Long bno);


    /*
    SELECT * FROM Reply r WHERE r.replyText =:replyText and r.replyer=:replyer 
    pageable는 sql문 뒤에 order by 절로 추가됨
    * */
    Page<Reply> findByReplyTextAndReplyer(String replyText, String replyer, Pageable pageable);

}
