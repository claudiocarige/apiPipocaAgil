package br.com.pipocaagil.apipipocaagil.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Pipoca RestFul API with Spring Boot 3 and Java 17 ")
                        .version("v1")
                        .description("This API provides access to information about Users")
                        .termsOfService("https://sua-api-exemplo.com/termos-de-servico")
                        .license(new License()
                                    .name("Apache 2.0")
                                    .url("https://springdoc.org")));
        /*Termos de serviços
        *
        * 1. Ao usar esta API, você concorda em cumprir as seguintes condições de uso.
        * 2. Acesso a informações sobre usuários
        * 3. Não é permitido usar esta API para fins ilegais, maliciosos ou que violem os direitos de terceiros.
        * 4. Não é permitido usar esta API para fins comerciais.
        * 5. Não é permitido usar esta API para fins de spam.
        * 6. Esta API coleta e utiliza dados dos usuários de acordo com nossa política de privacidade, que pode ser encontrada em [link para a política de privacidade].
        * 7. Esta API pode ser descontinuada a qualquer momento sem aviso prévio.
        * 8. Esta API é fornecida sem garantia de qualquer tipo.
        * 9. Para evitar o uso excessivo, impomos limites de taxa (rate limit) ao uso desta API. Consulte nossa documentação para obter detalhes sobre esses limites.
        * 10. Esta API pode ser usada apenas para fins educacionais.
        * Se você tiver alguma dúvida sobre esses termos de serviço ou sobre o uso da API, entre em contato conosco em [endereço de e-mail de suporte] ou [número de telefone de suporte].
        */
    }
}
