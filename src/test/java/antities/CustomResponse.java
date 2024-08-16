package antities;


import lombok.Data;

import java.util.List;

@Data
public class CustomResponse {
    private int category_id;
    private String category_title;
    private String category_description;
    private String created;
    private boolean flag;
    private String seller_name;
    private  int seller_id;
    List<CustomResponse> responses; // this is the list
    private String email;
}
