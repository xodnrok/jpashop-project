package jpabook7.jpashop7.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jpabook7.jpashop7.domain.Book;
import jpabook7.jpashop7.domain.Member;
import jpabook7.jpashop7.dto.OrderQueryDto;
import jpabook7.jpashop7.repository.OrderSearch;
import jpabook7.jpashop7.service.ItemService;
import jpabook7.jpashop7.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final ItemService itemService;
    private final OrderService orderService;

    //상품화면 보여주기
    @GetMapping("/order")
    public String createForm(Model model) {
        List<Book> items = itemService.findBooks();
        model.addAttribute("items", items);
        model.addAttribute("orderForm", new OrderForm());
        return "order/orderForm";
    }

//    //상품화면에서 주문받기
//    @PostMapping("/order")
//    public String order(@RequestParam Long itemId, @RequestParam int count, HttpServletRequest request) {
//
//        HttpSession session = request.getSession(false);
//        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
//
//        orderService.order(loginMember.getId(), itemId, count);
//
//        return "redirect:/orders";
//    }

    //상품화면에서 주문받기
    @PostMapping("/order")
    public String order(@Validated @ModelAttribute OrderForm orderForm , BindingResult bindingResult, HttpServletRequest request,Model model) {

        if (bindingResult.hasErrors()) {
            List<Book> items = itemService.findBooks();
            model.addAttribute("items", items);
            return "order/orderForm";
        }

        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        orderService.order(loginMember.getId(), orderForm.getItemId(), orderForm.getCount());

        return "redirect:/orders";
    }

    //주문목록 보여주기
    @GetMapping("/orders")
    public String orderList(Model model, HttpServletRequest request, @ModelAttribute OrderSearch orderSearch) {

        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        List<OrderQueryDto> orders = orderService.findAll(orderSearch, loginMember.getId());
        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    //주문취소
    @PostMapping("/orders/{orderId}/cancel")
    public String orderList(Model model, @PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
