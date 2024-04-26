package org.zerock.b01.domain;

import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/* DB 논리적 설계단계에서 물리적 설계로 전환되기 전에
물리적 Table로 생성되어야 할 논리적 묶음을 Entity라고 한다.

그래서 종종 Entity와 Table을 동일한 개념으로 사용하곤 한다.
* */

// 이 클래스의 정보를 가지고 자동으로 Table을 생성하겠다
// 특별한 이름을 주지 않으면 Board로 지정

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imageSet")
public class Board extends BaseEntity {

    /* @id는 Pk(primary Key)로 정의한다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    는 Mysql/MariaDB에서 사용하는 auto_increment 속성 부여
    * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(length=500, nullable=false) // 컬럼의 길이와 null 허용 여부
    private String title;

    @Column(length=2000, nullable=false)
    private String content;

    @Column(length=50, nullable=false)
    private String writer;

//    @OneToMany
//    Reply reply;

    /*
      Board         -           BoardImage
      부모           -            자식
      One(1)        -            Many(다)

      MappedBy = "board"는
      BoardImage의 private Board board; 필드를 FK로 지정한 것
      @OneToMany의 불필요한 복합연결 테이블 생성을 막을 수 있다.

      Cascade.ALL
      게시판의 부모 글이 삭제되면, 소속된 자식 이미지도 삭제되도록 한다.
    * */
    @OneToMany(mappedBy = "board",              //BoardImage의 board 변수
                cascade = {CascadeType.ALL},    // 상위 엔티티가 변할때 하위 엔티티도 함께 변해라
                fetch = FetchType.LAZY,
                orphanRemoval = true)           // 상위 엔티티의 참조가 더이상 없는 상태일 경우 삭제시키기 위해
    @Builder.Default
    @BatchSize(size = 20)       // 지정된 수를 IN 조건으로 사용(20을 부여하면 20개 단위로 묶어서 처리), OneToMany에서는 일반적으로 BatchSize를 줌
    private Set<BoardImage> imageSet = new HashSet<>();

    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void addImage(String uuid, String fileName){
        BoardImage boardImage = BoardImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .board(this)
                .ord(imageSet.size())
                .build();

        imageSet.add(boardImage);
    }

    public void clearImages(){

        imageSet.forEach(boardImage -> boardImage.changeBoard(null));

        this.imageSet.clear();
    }
}
