package com.study.backendprojectboard.controller;

import com.study.backendprojectboard.security.service.AuthTokenService;
import com.study.backendprojectboard.service.LoginService;
import com.study.backendprojectboard.user.model.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final AuthTokenService authTokenService;
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form, Authentication authentication) {
        if (authentication != null) {
            log.info("@@@ already Login [{}]", authentication);
            return "redirect:/";
        }
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL, HttpServletRequest request,
                        Authentication authentication) {

        if (authentication != null) {
            log.info("@@@ already Login [{}]", authentication);
            return "redirect:/";
        }
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        User loginUser = loginService.login(form.memberId, form.password);
        if (loginUser == null) {
            bindingResult.reject("loginFail", "아이디 혹은 비밀번호가 올바르지 않습니다.");
            return "login/loginForm";
        }
        // 로그인 성공한 사용자 -> 인증 데이터를 만들어줌: Session(서버) / Cookie(클라이언트)

        // Session
//        HttpSession session = request.getSession();
//        session.setAttribute(SessionConst.LOGIN_MEMBER, loginUser);

        // Cookie

        authTokenService.generateToken(loginUser);

        return "redirect:" + redirectURL;

    }

    @GetMapping("/logout")
    public String logout() {

        return "login/loginForm";
    }

    @AllArgsConstructor
    @Getter
    static class LoginForm {
        private String memberId;
        private String password;
    }
}
