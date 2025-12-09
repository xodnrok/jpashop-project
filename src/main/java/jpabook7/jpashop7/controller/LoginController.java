package jpabook7.jpashop7.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jpabook7.jpashop7.domain.Member;
import jpabook7.jpashop7.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    //로그인 로직
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login/loginForm";
    }

    //로그인 로직
    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult,
                        HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        try {

            Member loginMember = memberService.login(loginForm.getLoginId(), loginForm.getPassword());
            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        } catch (IllegalStateException e) {
            bindingResult.reject("loginFail", e.getMessage());
            return "login/loginForm";
        }

        return "redirect:/";
    }

    @PostMapping("/logout") //데이터를 요청하는게 아니라 Post 처리
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
