package uz.test.abitur.dtos.auth;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
    private String accessToken;
    private Date accessTokenExpiry;
    private String refreshToken;
    private Date refreshTokenExpiry;

    public TokenResponse(Date accessTokenExpiry, Date refreshTokenExpiry) {
        this.accessTokenExpiry = accessTokenExpiry;
        this.refreshTokenExpiry = refreshTokenExpiry;
    }
}
