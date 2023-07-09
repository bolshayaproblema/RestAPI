package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class LoginUnSuccessfulModel {
    private String Error;
}
