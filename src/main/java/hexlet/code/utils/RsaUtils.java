package hexlet.code.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Component
@ConfigurationProperties(prefix = "rsa") //читает значение из application.yaml
@Setter
@Getter
public class RsaUtils {
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;
}
