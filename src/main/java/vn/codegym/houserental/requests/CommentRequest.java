package vn.codegym.houserental.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {

    private String content;
    private Long userId;
    private Long houseId;
    private int star;
}
