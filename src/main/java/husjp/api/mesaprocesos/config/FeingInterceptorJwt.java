package husjp.api.mesaprocesos.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class FeingInterceptorJwt implements RequestInterceptor {

    private JwtContext jwtContext;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String token = jwtContext.getToken();
        if(token != null){
            requestTemplate.header("Authorization", "Bearer " + token);
        }

    }
}
