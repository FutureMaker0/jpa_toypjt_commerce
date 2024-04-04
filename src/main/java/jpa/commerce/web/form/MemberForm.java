package jpa.commerce.web.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수 입력사항 입니다.")
    private String memberName;

    private String country;
    private String city;
    private String zipcode;
}
