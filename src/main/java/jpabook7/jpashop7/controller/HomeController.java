package jpabook7.jpashop7.controller;

import jpabook7.jpashop7.domain.Item;
import jpabook7.jpashop7.domain.Member;
import jpabook7.jpashop7.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ItemService itemService;


    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {

        List<Item> items = itemService.findRecentItems();


        if (loginMember == null) {
            model.addAttribute("items", items);
            return "home";
        }

        model.addAttribute("member",loginMember);
        model.addAttribute("items", items);
        return "loginHome";

    }
}
