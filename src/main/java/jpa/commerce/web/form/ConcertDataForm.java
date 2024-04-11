package jpa.commerce.web.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@Data
public class ConcertDataForm {

    private Long id;

    @NotEmpty(message = "상품 이름은 필수 입력사항 입니다.")
    private String name;

    @NotNull
    @Range(min = 10000, max = 100000)
    private Integer price;

    @NotNull
    @Range(min = 100, max = 1000)
    private Integer stockQuantity;

    private String director;
    private String actor;

}
