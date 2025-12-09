package jpabook7.jpashop7.controller;

import jpabook7.jpashop7.domain.Address;
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

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원 가입 관련 로직
    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    //회원 가입 관련 로직
    @PostMapping("/members/new")
    public String create(@Validated @ModelAttribute MemberForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setLoginId(form.getLoginId());
        member.setPassword(form.getPassword());
        member.setName(form.getName());
        member.setAddress(address);

        try {
            memberService.join(member);
        } catch (IllegalStateException e) {
            bindingResult.rejectValue("loginId", "idError", e.getMessage());
            return "members/createMemberForm";
        }
        return "redirect:/";

    }

    //회원 목록 로직
    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
