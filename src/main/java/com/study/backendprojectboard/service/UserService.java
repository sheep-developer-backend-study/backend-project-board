package com.study.backendprojectboard.service;

import com.study.backendprojectboard.user.dto.UserFormDTO;
import com.study.backendprojectboard.user.model.User;
import com.study.backendprojectboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원 가입 폼 처리
     *
     * @param form View에서 넘어온 양식
     * @return {@literal List<FieldError>} - 검증 실패
     * <br>    null - 검증 성공
     * @see FieldError
     */
    public List<FieldError> register(UserFormDTO form) {
        List<FieldError> fieldErrors = validateUser(form, null);
        if (!fieldErrors.isEmpty()) return fieldErrors;

        User user = UserFormDTO.buildUser(form);
        userRepository.save(user);

        return null;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * 회원 정보 수정 폼 처리
     *
     * @param form View에서 넘어온 양식
     * @return {@literal List<FieldError>} - 검증 실패
     * <br>    null - 검증 성공
     * @see FieldError
     */
    public List<FieldError> updateMember(Long id, UserFormDTO form) {
        List<FieldError> fieldErrors = validateUser(form, id);
        if (!fieldErrors.isEmpty()) return fieldErrors;

        User user = UserFormDTO.buildUser(form);
        userRepository.save(user);
        return null;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteMember(User user) {
        userRepository.delete(user);
    }

    private List<FieldError> validateUser(UserFormDTO form, Long idIfEditing) {
        ArrayList<FieldError> exceptions = new ArrayList<>();

        exceptions.add(validateEmail(form.getEmail(), idIfEditing));
        exceptions.add(validateNickname(form.getName(), idIfEditing));

        return exceptions.stream().filter(Objects::nonNull).toList();
    }

    private FieldError validateEmail(String email, Long idIfEditing) {
        Optional<User> found = userRepository.findByEmail(email);
        // findAllByEmail   select  from where email = # {email}  all = list find = list가나올수가없다
        if (found.isPresent()) {
            // 본인 정보 수정 시 패스
            if (found.get().getUserId().equals(idIfEditing)) return null;

            FieldError error = new FieldError("user", "email", email, false, null,
                    null, "이미 사용중인 아이디입니다.");
            log.error("validation memberId [{}] failed: Already Exist", email);

            return error;
        }
        return null;
    }

    private FieldError validateNickname(String name, Long idIfEditing) {
        Optional<User> found = userRepository.findAll().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
        // 이름은 여러가지를  db에 가질수있지만 어플리케이션에 는한가지만가질수있다

        if (found.isPresent()) {
            // 본인 정보 수정 시 패스
            if (found.get().getUserId().equals(idIfEditing)) return null;

            FieldError error = new FieldError("user", "name", name, false, null,
                    null, "이미 사용중인 닉네임입니다.");
            log.error("validation name [{}] failed: Already Exist", name);
            return error;
        }
        return null;
    }


}
