package com.study.backendprojectboard.user.dto;

import com.study.backendprojectboard.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserFormDTO {


    private String email;

    private String password;

    private String name;

    public static User buildUser(UserFormDTO userFormDTO) {
        return User.builder()
                .email(userFormDTO.getEmail())
                .name(userFormDTO.getName())
                .password(userFormDTO.getPassword())
                .build();
    }

}
