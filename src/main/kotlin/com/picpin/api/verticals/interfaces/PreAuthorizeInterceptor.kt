package com.picpin.api.verticals.interfaces

import com.picpin.api.oauth.domains.AccessTokenParser
import com.picpin.api.verticals.domains.account.AccountHolder
import com.picpin.api.verticals.interfaces.model.JsonAccessToken
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

class PreAuthorizeInterceptor(
    private val accessTokenParser: AccessTokenParser
) : HandlerInterceptor {
    private val logger: KLogger = KotlinLogging.logger { }

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        val jsonAccessToken = JsonAccessToken(
            authorizationHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION),
            requestUrl = request.requestURL.toString()
        )
        val parsedAccessToken = accessTokenParser.parse(jsonAccessToken.payload)
        AccountHolder.setAccountId(parsedAccessToken.id.toLong())

        return super.preHandle(request, response, handler)
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        AccountHolder.refresh()
        super.postHandle(request, response, handler, modelAndView)
    }
}
