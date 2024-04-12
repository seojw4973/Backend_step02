package org.zerock.w2.domain;

import lombok.*;

@Getter     // getter만 구현
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {
    private String mid;
    private String mpw;
    private String mname;
    private String uuid;
}
