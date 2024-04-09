package org.zerock.jdbcex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder                // 빌더 패턴 사용, 초기화 시 필드 초기화처럼 가독성 증가 효과
@Data                   // getter/setter/toString/equals/hashCode 모두 오버라이딩
@NoArgsConstructor      // 매개변수 없는 생성자
@AllArgsConstructor     // 모든 필드를 초기화할 수 잇는 생성자
public class TodoDTO {
    private Long tno;
    private String title;
    private LocalDate dueDate;
    private boolean finished;
}
