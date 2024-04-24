package org.zerock.b01.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

    private Long rno;

    @NotNull
    private Long bno;

    @NotEmpty
    private String replyText;

    @NotEmpty
    private String replyer;

    // json으로 변환할 때 날짜를 이 형식으로 만든다
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;

    // json으로 변환하지 않으므로, 전송하지 않는다.
    @JsonIgnore
    private LocalDateTime modDate;

}
