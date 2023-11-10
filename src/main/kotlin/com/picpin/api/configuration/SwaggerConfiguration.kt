package com.picpin.api.configuration

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfiguration {

    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .components(Components())
        .info(getApiInfo())

    private fun getApiInfo() = Info()
        .title("PICPIN API")
        .description("PICPIN API DESCRIPTION")
        .version("1.0.0")
}
