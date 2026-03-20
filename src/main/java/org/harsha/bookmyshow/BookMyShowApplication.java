package org.harsha.bookmyshow;

import org.harsha.bookmyshow.controllers.UserController;
import org.harsha.bookmyshow.dto.ResponseStatus;
import org.harsha.bookmyshow.dto.SignUpRequestDto;
import org.harsha.bookmyshow.dto.SignUpResponseDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookMyShowApplication implements CommandLineRunner {

    private final UserController userController;

    public BookMyShowApplication(UserController userController) {
        this.userController = userController;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookMyShowApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setUsername("test3@gmail.com");
        signUpRequestDto.setPassword("password");

        SignUpResponseDto signUpResponseDto = userController.signUp(signUpRequestDto);
        if (signUpResponseDto.getStatus().equals(ResponseStatus.SUCCESS)) {
            System.out.println("User created successfully");
        } else {
            System.out.println("User creation failed");
        }

    }

}
