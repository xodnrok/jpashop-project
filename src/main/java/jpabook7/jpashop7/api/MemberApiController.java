package jpabook7.jpashop7.api;

import jakarta.validation.constraints.NotEmpty;
import jpabook7.jpashop7.domain.Member;
import jpabook7.jpashop7.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    //등록
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Validated Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    //등록
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Validated CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());
        member.setPassword(request.getPassword());
        member.setLoginId(request.getLoginId());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    //수정
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable Long id, @RequestBody @Validated UpdateMemberRequest request) {
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }


    //조회
    @GetMapping("/api/v1/members")
    public Result<List<MemberDto>> membersV2() {
        List<MemberDto> list = memberService.findMembers().stream()
                .map(member -> new MemberDto(member.getName()))
                .toList();
        return new Result<>(list.size(), list);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {

        private int count;
        private T data;

    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }


    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {

        private Long id;
        private String name;

    }




    @Data
    static class CreateMemberRequest {

        @NotEmpty(message = "이름은 비우면 안됩니다.")
        private String name;

        private String loginId;

        @NotEmpty(message = "패스워드를 비우면 안됩니다.")
        private String password;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
