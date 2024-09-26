package husjp.api.mesaprocesos.request;

import husjp.api.mesaprocesos.service.dto.AuthenticationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "solutionv2Gateway",
        value = "solutionv2Gateway",
        path = "/"
        //url = "${solution.api.gateway.url}"
)
public interface ApiGatewayServiceRequest {

    @PostMapping(value = "excel/export-excel")
    byte[] exportExcel(@RequestBody Object data);

    @PostMapping("auth/login-microservices")
    AuthenticationResponse loginMicroservices(@RequestBody Object request);

}
