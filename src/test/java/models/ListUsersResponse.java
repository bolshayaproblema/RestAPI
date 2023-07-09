package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.LinkedList;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ListUsersResponse {
    LinkedList<User>  data;
}
