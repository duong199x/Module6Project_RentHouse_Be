package vn.codegym.houserental.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishListRequest {
    private Long userId;
    private Long houseId;
}
