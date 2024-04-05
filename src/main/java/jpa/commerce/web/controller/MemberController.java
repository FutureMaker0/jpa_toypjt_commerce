package jpa.commerce.web.controller;

import jpa.commerce.domain.Address;
import jpa.commerce.domain.Member;
import jpa.commerce.repository.MemberRepository;
import jpa.commerce.service.MemberService;
import jpa.commerce.web.form.MemberDataForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @GetMapping("/members/regist")
    public String createForm(Model model) {
        model.addAttribute("memberDataForm", new MemberDataForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/regist")
    public String createMember(@Validated MemberDataForm memberDataForm, BindingResult errorResult) {
        if (errorResult.hasErrors()) {
            return "members/createMemberForm";
        }
        Member member = new Member();
        Address address = new Address(memberDataForm.getCountry(), memberDataForm.getCity(), memberDataForm.getZipcode());

        member.setName(memberDataForm.getName());
        member.setAddress(address);
        memberService.registMember(member);

        return "redirect:/members";
    }

    @GetMapping("/members")
    public String memberList(Model model) {
        List<Member> allMembers = memberService.findAllMembers();
        model.addAttribute("allMembers", allMembers);
        return "members/memberList";
    }


}