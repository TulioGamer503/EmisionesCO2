package sv.edu.udb.emisiones.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sv.edu.udb.emisiones.controller.request.EmisionRequest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmisionIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void whenGetAllEmisiones_thenReturnOk() {
        // when
        ResponseEntity<String> response = restTemplate.getForEntity("/api/emisiones", String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void whenGetAnalisisVariacion_thenReturnOk() {
        // when
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/api/emisiones/analisis/variacion?anioBaseInicio=2015&anioBaseFin=2019&anioComparacion=2020",
                String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void whenGetSectores_thenReturnOk() {
        // when
        ResponseEntity<String> response = restTemplate.getForEntity("/api/sectores", String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}