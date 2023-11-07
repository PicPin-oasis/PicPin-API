package com.picpin.api.aspect

import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class AccountArgumentResolver : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        val hasRequiredAccountId = parameter.getParameterAnnotation(AccountId::class.java) != null
        val hasLongTypeParameter = parameter.parameterType == Long::class.java

        return hasRequiredAccountId && hasLongTypeParameter
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any = AccountHolder.getAccountId()
}
