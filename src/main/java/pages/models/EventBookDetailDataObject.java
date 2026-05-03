package pages.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventBookDetailData {
    private String fullName;
    private String email;
    private String phoneNumber;
}
