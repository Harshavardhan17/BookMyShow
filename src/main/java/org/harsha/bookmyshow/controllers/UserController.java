package org.harsha.bookmyshow.controllers;

import org.harsha.bookmyshow.dto.ResponseStatus;
import org.harsha.bookmyshow.dto.SignUpResponseDto;
import org.harsha.bookmyshow.dto.SignUpRequestDto;
import org.harsha.bookmyshow.models.User;
import org.harsha.bookmyshow.services.UserService;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
        try {
            User user = userService.singUp
                    (signUpRequestDto.getUsername(), signUpRequestDto.getPassword());
            signUpResponseDto.setStatus(ResponseStatus.SUCCESS);
            signUpResponseDto.setUserId(user.getId());
        } catch (Exception e) {
            signUpResponseDto.setStatus(ResponseStatus.FAILURE);
            signUpResponseDto.setErrorMessage(e.getMessage());
        }

        return signUpResponseDto;
    }
}
