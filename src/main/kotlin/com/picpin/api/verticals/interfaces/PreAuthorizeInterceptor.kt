package com.picpin.api.verticals.interfaces

import com.picpin.api.oauth.domains.access.AccessTokenParser
import com.picpin.api.verticals.interfaces.model.AccountHolder
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

class PreAuthorizeInterceptor(
    private val accessTokenParser: AccessTokenParser
) : HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        /*val jsonAccessToken = JsonAccessToken(
            authorizationHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION)
        )
        val parsedAccessToken = accessTokenParser.parse(jsonAccessToken.payload)*/
        // AccountHolder.setAccountId(parsedAccessToken.id.toLong())
        AccountHolder.setAccountId(1L)
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
