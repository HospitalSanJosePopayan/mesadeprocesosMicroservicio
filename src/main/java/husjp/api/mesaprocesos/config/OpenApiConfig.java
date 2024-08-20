package husjp.api.mesaprocesos.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi AreaServicio(@Value("2.3.0") String appVersion){
        String [] paths = {"/AreaServicio/**"};
        return GroupedOpenApi.builder()
                .group("AreaServicio")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Area Servicio").version(appVersion)
                        .description("metodos que obtienen las Areas de Servicio Hospital San Jose ")))
                .pathsToMatch(paths)
                .build();
    }
    
    @Bean
    public GroupedOpenApi ProcesosOpenApi(@Value("2.3.0") String appVersion){
        String [] paths = {"/procesos/**"};
        return GroupedOpenApi.builder()
                .group("Procesos")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("procesos API").version(appVersion)
                        .description("procesos de Areas de Servicio del Hospital San jose   ")))
                .pathsToMatch(paths)
                .build();
    }
    
    @Bean
    public GroupedOpenApi SubProcesosOpenApi(@Value("2.3.0") String appVersion){
      String [] paths = {"/subprocesos/**"};
        return GroupedOpenApi.builder()
                .group("subProcesos")
               .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("procesos API").version(appVersion)
                        .description("subprocesos de  Procesos de Areas de Servicio del Hospital San jose   ")))
               .pathsToMatch(paths)
                .build();
    }
    
    @Bean
    public GroupedOpenApi UsuarioProceso(@Value("2.3.0") String appVersion){
        String [] paths = {"/usuarioprocesos/**"};
        return GroupedOpenApi.builder()
                .group("usuarioprocesos")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Servicios API").version(appVersion)
                        .description("Usuario procesos")))
                .pathsToMatch(paths)
                .build();
    }
}
