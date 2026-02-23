package pages.models;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactFormData {
    private String name;
    private String email;
    private String subject;
    private String message;
}
