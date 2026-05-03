package pages.models;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsData {
    private String userEmail;
    private String userPassword;
    private String confirmPassword;
}
