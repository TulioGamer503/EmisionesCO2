/**
 * Configura la documentación automática de la API utilizando OpenAPI (Swagger 3).
 *.
 * Esta clase define los metadatos que aparecerán en la interfaz Swagger UI,
 * como el título, la descripción, la versión y los datos de contacto del proyecto.
 *.
 * Con esta configuración, los endpoints REST documentados con anotaciones
 * @Tag y @Operation serán visibles y navegables desde:
 *    → http://localhost:8080/swagger-ui/index.html
 *.
 * Beneficio:
 * Facilita la exploración, prueba y comprensión de la API por parte de
 * desarrolladores, investigadores y usuarios técnicos.
 */

package sv.edu.udb.emisiones.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // Indica que esta clase contiene configuraciones de Spring
public class OpenApiConfig {

    /**
     * Crea y registra un bean de tipo OpenAPI.
     *
     * Este método personaliza la información que se mostrará en la
     * documentación generada por Swagger/OpenAPI.
     *
     * @return un objeto OpenAPI configurado con los datos del proyecto.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        // Título que aparecerá en la interfaz Swagger
                        .title("API de Análisis de Emisiones CO2 - El Salvador")

                        // Descripción general del propósito de la API
                        .description("Sistema para análisis de variación de emisiones de CO2 durante la pandemia (2020-2021) vs línea base (2015-2019)")

                        // Versión actual del API
                        .version("1.0.0")

                        // Información de contacto del equipo responsable
                        .contact(new Contact()
                                .name("Equipo de Investigación")
                                .email("investigacion@udb.edu.sv")));
    }
}
