package com.example.arom1.controller;

import com.example.arom1.dto.MemberDto;
import com.example.arom1.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public String signup(MemberDto request) {
        memberService.save(request);

        return "redirect:/login";
    }
}
