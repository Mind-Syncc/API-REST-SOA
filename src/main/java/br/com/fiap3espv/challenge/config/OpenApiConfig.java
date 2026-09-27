package br.com.fiap3espv.challenge.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Challenge FIAP - Ford Service Share")
                        .description("API REST para gerenciamento de clientes, ordens de serviço, usuários e autenticação")
                        .version("v1")
                        .contact(new Contact()
                                .name("Heloísa Fleury Jardim - RM556378\n" +
                                        "Juan Fuentes Rufino - RM557673\n" +
                                        "Rickelmyn de Souza Ruescas - RM556055\n" +
                                        "Paulo Henrique Monteiro Golovanevsky - RM555300\n" +
                                        "Pedro Henrique Silva Batista - RM558137")));
    }
}
