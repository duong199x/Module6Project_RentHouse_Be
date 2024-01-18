package vn.codegym.houserental.requests;

import lombok.Data;
import vn.codegym.houserental.model.Convenient;

import java.util.List;
import java.util.Set;
@Data
public class SearchRequest {
    private String name;
    private String location;
    private Long categoryId;
    private double minPrice;
    private double maxPrice;
    private List<Long> convenientIds;
}
