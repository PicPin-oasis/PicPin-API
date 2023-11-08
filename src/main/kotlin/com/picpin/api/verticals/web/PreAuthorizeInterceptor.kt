package com.picpin.api.verticals.web

import com.picpin.api.domains.oauth.AccessTokenParser
import com.picpin.api.verticals.domain.BusinessErrorCode
import com.picpin.api.verticals.domain.BusinessException
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
        val splittingAccessToken = accessToken.split(" ")
        validateAccessToken(splittingAccessToken, request, accessToken)

        val parsedAccessToken = accessTokenParser.parse(splittingAccessToken.last())
        val userId = parsedAccessToken.id.toLong()

        AccountHolder.setAccountId(userId)
        return super.preHandle(request, response, handler)
    }

    private fun validateAccessToken(
        splittingAccessToken: List<String>,
        request: HttpServletRequest,
        accessToken: String,
    ) {
        if (splittingAccessToken.isEmpty() || splittingAccessToken.size != 2) {
            BusinessException.of(
                BusinessErrorCode.JWT_CREATE_FAILED,
                " requestUrl = ${request.requestURL} accessToken = $accessToken"
            )
        }
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
