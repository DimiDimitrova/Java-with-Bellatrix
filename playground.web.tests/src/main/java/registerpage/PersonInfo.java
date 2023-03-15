package registerpage;

import lombok.Getter;
import lombok.Setter;

public class PersonInfo {
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
    @Getter @Setter private String email;
    @Getter @Setter private String telephone;
    @Getter @Setter private String password;
}