package jpabook7.jpashop7.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String loginId; //아이디

    private String password; //비밀번호

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();


}
