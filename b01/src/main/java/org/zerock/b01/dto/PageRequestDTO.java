package org.zerock.b01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page=1;

    @Builder.Default
    private int size = 10;  // 1개 페이지의 갯수

    private String type;    // 검색 타입(들...), t, c, w, tc, tw, twc

    private String keyword; // 검색어

    /* type이 "tcw"일 경우
    getTypes()는 ["t", "c", "w"]를 리턴한다.
    * */

    public String[] getTypes(){
        if(type == null || type.isEmpty()){
            return null;
        }
        return type.split("");
    }

    /* String...props 가변 매개변수
    아래처럼 매개변수의 갯수가 정해지지 않고 여러 개가 올 수 있다.
    이 매개변수는 Sort.by(props)에 전달되어
    전달된 매개변수 순서대로 ORDER BY를 하게 된다.
    getPageable("bno");
    getPageable("bno", "writer");
    getPageable("bno", "content", "writer", "regDate");
    * */

    public Pageable getPageable(String...props){
        return PageRequest.of(this.page -1, this.size, Sort.by(props).descending());
    }

    private String link;

    public String getLink() {
        if (link == null) {
            StringBuilder builder = new StringBuilder();

            builder.append("page = " + this.page);

            builder.append("&size = " + this.size);

            if (type != null && type.length() > 0) {
                builder.append("&type=" + type);
            }
            if (keyword != null) {
                try {
                    builder.append("&keyword = " + URLEncoder.encode(keyword, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                }
            }
            link = builder.toString();
        }
        return link;
    }
}
