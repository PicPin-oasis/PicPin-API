package com.picpin.api.aspect

import com.picpin.api.domains.oauth.AccessTokenParser
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.lang.RuntimeException

class PreAuthorizeInterceptor(
    private val accessTokenParser: AccessTokenParser
) : HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        val accessToken = request.getHeader(HttpHeaders.AUTHORIZATION) ?: throw RuntimeException()
        val parsedAccessToken = accessTokenParser.parse(accessToken.split(" ").last())
        val userId = parsedAccessToken.id.toLong()

        AccountHolder.setAccountId(userId)

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
