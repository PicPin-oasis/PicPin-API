package com.picpin.api.verticals.configuration

import com.picpin.api.oauth.domains.access.AccessTokenParser
import com.picpin.api.verticals.interfaces.AccountIdResolver
import com.picpin.api.verticals.interfaces.PreAuthorizeInterceptor
import com.picpin.api.verticals.interfaces.exception.PreAuthorizationInterceptorExceptionResolver
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfiguration {

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
    fun webMvcConfigurer(accessTokenParser: AccessTokenParser): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addInterceptors(registry: InterceptorRegistry) {
                registry.addInterceptor(PreAuthorizeInterceptor(accessTokenParser))
                    .addPathPatterns("/**")
                    .excludePathPatterns(
                        "/",
                        "/oauth2/code/kakao",
                        "/health-check",
                        "/error",
                        "/favicon.ico",
                        "/v3/api-docs/**"
                    )
            }

            override fun extendHandlerExceptionResolvers(resolvers: MutableList<HandlerExceptionResolver>) {
                resolvers.add(PreAuthorizationInterceptorExceptionResolver())
            }

            override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
                resolvers.add(AccountIdResolver())
            }
        }
    }
}
