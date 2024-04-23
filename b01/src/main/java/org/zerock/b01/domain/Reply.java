package org.zerock.b01.domain;

import lombok.*;

import javax.persistence.*;

@Entity             // JPA에 의해 Table로 생성된다.
/*
테이블에 구체적인 이름을 부여할 때 사용
그리고 fk에 index를 만들어 주었다.
pk는 자동으로 index가 생성되어 있다.
그런데 fk는 자동으로 index가 생성되어 있지 않으므로
join을 해서 특정 검색을 하게 되면
검색 속도는 index가 없는 fk에 맞춰지게 된다.

그러므로 fk를 선언하면 나중에 join을 통한 검색을 할 때
속도를 향상시키기 위해 일반적으로 fk에 index를 설정한다.
*/
@Table(name="Reply", indexes = {
        @Index(name="idx_reply_board_bno", columnList = "board_bno")
})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString( exclude = "board")   // board 필드만 제외하고 나머지 정보만 보여준다.
public class Reply extends BaseEntity{

    // PK, 주키, 식별자, Auto_increment(1씩 자동증가)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    /* Reply  -  Board
       (Many)    (One)

       Reply 테이블로부터 정보를 읽어들일 때
       FetchType.LAZY : 일단은 대체 객체로 채워놓고, 나중에 필요할때 읽어들인다.
       FetchType.EAGER : Board도 테이블에서 즉시 읽어들인다.

        DBMS에서는 Board가 부모, Reply가 자식인 제약조건이 생성된다.
        (Board테이블의 pk가 Reply테이블의 fk로 전이된다.)

        단방향 연결방식에서는 @OneToMany를 권장하지 않고, @ManyToOne를 권장한다.
        왜냐하면 @OneToMany를 사용하면(Board클래스에 Reply reply변수를 추가하고 @OneToMany설정)
        Reply에 새로운 데이터를 넣을 때 불필요한 Update과정이 추가로 필요하기 때문에
        아무래도 좀더 부하가 걸린다.
        그러므로 자식테이블의 클래스에 부모늬 클래스 변수를 포함하고 @ManyToOne을 설정한다.


    * */
    @ManyToOne(fetch = FetchType.LAZY)
//    @ManyToOne(fetch = FetchType.EAGER)
    private Board board;

    private String replyText;

    private String replyer;
}
