package jpa.commerce.web.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConcertDataForm {

    private Long id;

    @NotEmpty(message = "상품 이름은 필수 입력사항 입니다.")
    private String name;
    private int price;
    private int stockQuantity;

    private String director;
    private String actor;

}
