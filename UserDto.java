package nl.jrevolt.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.jrevolt.app.model.Role;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String email;
    private String password;
    private String confirmPassword;
    private boolean isAccountVerified;
    private Set<Role> roles = new HashSet<>();
    private String firstName;
    private String lastName;
    @JsonProperty("customer_number")
    private String customerNumber;

    private String userImage;

    public UserDto(Long id, String email, String password, String confirmPassword, Set<Role> roles, boolean isAccountVerified) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.roles = roles;
        this.isAccountVerified = isAccountVerified;
    }

    public UserDto(Long id, String email, String password, String confirmPassword, boolean isAccountVerified, Set<Role> roles, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.isAccountVerified = isAccountVerified;
        this.roles = roles;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDto(Long id, String email, String password, String confirmPassword, boolean isAccountVerified, Set<Role> roles, String firstName, String lastName, String customerNumber) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.isAccountVerified = isAccountVerified;
        this.roles = roles;
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerNumber = customerNumber;
    }
}
