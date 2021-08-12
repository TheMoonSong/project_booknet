package org.moonsong.booknet.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRequest {
    private String email;
    private String password;
    private String nickName;
}
