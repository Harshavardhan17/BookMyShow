package org.harsha.bookmyshow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponseDto {
    private long userId;
    private ResponseStatus status;
    private String errorMessage;
}
