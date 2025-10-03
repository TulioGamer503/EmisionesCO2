package sv.edu.udb.emisiones.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Análisis de Emisiones CO2 - El Salvador")
                        .description("Sistema para análisis de variación de emisiones de CO2 durante la pandemia (2020-2021) vs línea base (2015-2019)")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo de Investigación")
                                .email("investigacion@udb.edu.sv")));
    }
}
