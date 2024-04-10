package jpa.commerce.web.form;

import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotEmpty;
import jpa.commerce.domain.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDataForm {

    @NotEmpty(message = "회원 이름은 필수 입력사항 입니다.")
    private String name;

    private String country;
    private String city;
    private String zipcode;

}
