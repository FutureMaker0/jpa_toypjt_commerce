package jpa.commerce.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchOption {

    private String memberName;
    private OrderStatus orderStatus; //[ORDER, CANCEL]
    
}
