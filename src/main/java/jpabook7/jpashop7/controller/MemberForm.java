package jpabook7.jpashop7.controller;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotEmpty;
import jpabook7.jpashop7.domain.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {

    @NotEmpty(message = "아이디를 입력해주세요")
    private String loginId; //아이디

    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String password; //비밀번호

    @NotEmpty(message = "이름을 입력하세요")
    private String name; //이름

    @NotEmpty(message = "도시를 입력하세요")
    private String city; //도시

    @NotEmpty(message = "거리를 입력해주세요")
    private String street; //거리

    @NotEmpty(message = "우편번호를 입력하세요")
    private String zipcode; //우편번호
}
