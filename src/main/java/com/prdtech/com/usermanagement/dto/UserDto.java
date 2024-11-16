package com.prdtech.com.usermanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDto(
        Long id,
        String userName,
        String email,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) String password) {
}
