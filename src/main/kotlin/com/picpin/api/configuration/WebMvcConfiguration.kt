package com.picpin.api.configuration

import com.picpin.api.aspect.AccountArgumentResolver
import com.picpin.api.aspect.PreAuthorizeInterceptor
import com.picpin.api.domain.oauth.AccessTokenParser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfiguration(
    private val accessTokenParser: AccessTokenParser
) {

    fun preAuthorizeInterceptor() = PreAuthorizeInterceptor(accessTokenParser)

    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedOrigins(
                        "http://localhost:3000",
                        "https://picpin.life",
                        "https://www.picpin.life"
                    )
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true)
            }

            override fun addInterceptors(registry: InterceptorRegistry) {
                registry.addInterceptor(preAuthorizeInterceptor())
                    .excludePathPatterns("/oauth2/code/kakao", "/health-check", "/error", "/favicon.ico")
                    .addPathPatterns("/**")
            }

            override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
                resolvers.add(AccountArgumentResolver())
            }
        }
    }
}
