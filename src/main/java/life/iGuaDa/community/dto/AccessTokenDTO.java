package life.iGuaDa.community.dto;

import lombok.Data;

/**
 * 登录所用传参
 * Created by codedrinker on 2019/4/24.
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
