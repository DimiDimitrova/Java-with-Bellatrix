package accountvoucherpage;

import lombok.Getter;
import lombok.Setter;

public class RecipientInfo {
    @Getter @Setter private String recipientName;
    @Getter @Setter private String recipientEmail;
    @Getter @Setter private String recipientPassword;
}