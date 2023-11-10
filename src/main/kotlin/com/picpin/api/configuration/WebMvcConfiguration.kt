package com.picpin.api.configuration

import com.picpin.api.domains.oauth.AccessTokenParser
import com.picpin.api.verticals.web.AccountArgumentResolver
import com.picpin.api.verticals.web.PreAuthorizeInterceptor
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfiguration(
    private val accessTokenParser: AccessTokenParser
) {

    fun preAuthorizeInterceptor() = PreAuthorizeInterceptor(accessTokenParser)

    @Bean
    fun simpleCorsFilter(): FilterRegistrationBean<CorsFilter> {
        val configurationSource = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOriginPattern("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        config.maxAge = 3600

        configurationSource.registerCorsConfiguration("/**", config)
        val filterRegistrationBean = FilterRegistrationBean(CorsFilter(configurationSource))
        filterRegistrationBean.order = Ordered.HIGHEST_PRECEDENCE

        return filterRegistrationBean
    }

    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addInterceptors(registry: InterceptorRegistry) {
                registry.addInterceptor(preAuthorizeInterceptor())
                    .excludePathPatterns(
                        "/oauth2/code/kakao",
                        "/health-check",
                        "/error",
                        "/favicon.ico",
                        "/v3/api-docs/**"
                    )
                    .addPathPatterns("/**")
            }

            override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
                resolvers.add(AccountArgumentResolver())
            }
        }
    }
}
