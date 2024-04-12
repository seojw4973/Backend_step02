package org.zerock.w2.filter;

import lombok.extern.log4j.Log4j2;
import org.zerock.w2.dto.MemberDTO;
import org.zerock.w2.service.MemberService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;


@WebFilter(urlPatterns = {"/todo/*"})
@Log4j2
public class LogInCheckFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.info("Login check filter...");

        // 로그인 정보가 세션이 존재하는지 여부를 판단
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession();

        // 세션에 로그인정보가 있으니 바로 서블릿으로 전송
        if(session.getAttribute("loginInfo") != null) {
            chain.doFilter(request, response);
            return;
        }

        // session에 logInfo값이 없지만, 브라우저에서 remember-me를 보냈는지 찾는다.
        // 쿠키를 체크
        Cookie cookie = findCookie(req.getCookies(), "remember-me");

        // 세션에도 없고 쿠키에도 없다면 그냥 로그인 화면으로 보냄
        if(cookie == null) {
            resp.sendRedirect("/login");
            return;
        }

        // 쿠키가 존재하는 상황이라면
        log.info("cookie가 존재하는 상황");
        
        // remember-me의 value를 추출
        String uuid = cookie.getValue();

        try{
            // 브라우저의 remember-me 쿠키값과 maria-db의 uuid값이 일치하는 지
            MemberDTO memberDTO = MemberService.INSTANCE.getByUUID(uuid);

            log.info("쿠키의 값으로 조회한 사용자 정보: " + memberDTO);
            
            //remember-me의 정보가 Maria-db의 uuid와 일치하지 않는다.
            if(memberDTO == null) {
                throw new Exception("Cookie value is not valid");
            }
            // 회원 정보를 세션에 추가
            // 쿠키값과 db값이 일치하면 loginInfo를 세션에 저장하고 서블릿 전송
            session.setAttribute("loginInfo", memberDTO);
            chain.doFilter(request, response);

        }catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/login");
        }
    }

    private Cookie findCookie(Cookie[] cookies, String name) {

        if(cookies == null || cookies.length==0) {
            return null;
        }
        
        // Cookie 객체를 얻는데, 추가로 Optional에서 제공하는 메서드를 사용할 수 있음.
        Optional<Cookie> result = Arrays.stream(cookies)
                .filter(ck-> ck.getName().equals(name))
                .findFirst();

        // result가 정상이면 Cookie 객체를 리턴, 아니면 null값을 리턴
        return result.isPresent() ? result.get() : null;
    }
}
