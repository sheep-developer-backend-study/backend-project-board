package com.study.backendprojectboard.controller;

import com.study.backendprojectboard.service.UserService;
import com.study.backendprojectboard.user.dto.UserFormDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 목록
    @GetMapping("")
    public String userList(Model model) {
        log.info("userList() call");
        model.addAttribute("users", userService.findAll());
        return "members/memberList";
    }

    // 회원 가입 폼 호출
    @GetMapping("/add")
    public String addMemberForm(@ModelAttribute("member") UserFormDTO userFormDTO) {
        log.info("addMemberForm() call - GET");
        return "members/addMemberForm";
    }

    // 회원 가입 처리
    @PostMapping("/add")
    public String addMember(@Validated @ModelAttribute("member") UserFormDTO memberFormDTO, BindingResult bindingResult) {
        log.info("addMember() call - POST");

        // 도메인 Validation 처리 -> @Validated + BindingResult로 처리

        // 서비스 Validation throw 처리
        List<FieldError> hasFieldErrors = null;
        if (!bindingResult.hasErrors()) {   // 바인딩에 문제가 없으면 가입 시도
            hasFieldErrors = userService.register(memberFormDTO);
        }

        if (hasFieldErrors != null) { // validation에 문제가 있었으면 에러 추가
            for (FieldError fieldError : hasFieldErrors) {
                bindingResult.addError(fieldError);
            }
        }

        if (bindingResult.hasErrors()) { // bindingResult 에러 뿌리고 되돌아가기
            log.error("addMember() ex = {}", bindingResult);
            return "members/addMemberForm";
        }

        return "redirect:/members";
    }

    @GetMapping("/edit/{id}")
    public String editMemberForm(@PathVariable("id") Long id, Model model) {
        log.info("editMemberForm() call - GET");
        model.addAttribute("member", userService.findById(id));
        return "members/editMemberForm";
    }

    @PostMapping("/edit/{id}")
    public String editMember(@PathVariable("id") Long id,
                             @Validated @ModelAttribute("member") UserFormDTO memberFormDTO, BindingResult bindingResult) {
        log.info("editMemberForm() call - POST");

        List<FieldError> hasFieldErrors = null;
        if (!bindingResult.hasErrors()) {   // 바인딩에 문제가 없으면 업데이트 시도
            hasFieldErrors = userService.updateMember(id, memberFormDTO);
        }

        if (hasFieldErrors != null) {   // 업데이트 중 Validation에 문제가 있었으면 bindingResult에 추가
            for (FieldError fieldError : hasFieldErrors) {
                bindingResult.addError(fieldError);
            }
        }

        if (bindingResult.hasErrors()) {    // bindingResult 뿌리고 되돌아가기
            log.error("editMember() ex = {}", bindingResult);
            return "members/editMemberForm";
        }

        userService.updateMember(id, memberFormDTO);
        return "redirect:/members";
    }
}
