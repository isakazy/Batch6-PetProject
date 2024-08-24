package antities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CustomResponse {
    private int category_id;
    private String category_title;
    private String category_description;
    private String created;
    private boolean flag;
    private String seller_name;
    private  int seller_id;
    private String phone_number;
    List<CustomResponse> responses; // this is the list
    private String email;
    private String responseBody;
}
