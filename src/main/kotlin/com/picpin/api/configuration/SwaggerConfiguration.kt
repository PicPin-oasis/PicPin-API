package com.picpin.api.configuration

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class SwaggerConfiguration {

    @Profile("default")
    @Bean
    fun localOpenAPIConfig(): OpenAPI = OpenAPI()
        .components(Components())
        .info(getApiInfo())

    @Profile("production")
    @Bean
    fun productionOpenAPIConfig(): OpenAPI {
        val productionServer = Server().url("https://api.picpin.life")
        return OpenAPI()
            .components(Components())
            .servers(listOf(productionServer))
            .info(getApiInfo())
    }

    private fun getApiInfo() = Info()
        .title("PICPIN API")
        .description("PICPIN API DESCRIPTION")
        .version("1.0.0")
}
