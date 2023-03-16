package configuration;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LambdaTestSettings {
    @SerializedName("registeredUserName")
    public String registeredUserName;
    @SerializedName("registeredPassword")
    public String registeredPassword;
}
