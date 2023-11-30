package com.picpin.api.verticals.interfaces.exception

import com.picpin.api.verticals.domains.exception.BusinessException
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.ModelAndView
import java.io.IOException
import java.lang.Exception

class PreAuthorizationInterceptorExceptionResolver : HandlerExceptionResolver {
    private val logger: KLogger = KotlinLogging.logger { }

    override fun resolveException(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any?,
        exception: Exception
    ): ModelAndView? {
        return try {
            if (exception is BusinessException) {
                logger.info { "${request.requestURL} ${exception.errorCode} ${exception.reason}" }
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, exception.localizedMessage)
                ModelAndView()
            } else {
                logger.info { "${request.requestURL} ${exception.cause} ${exception.localizedMessage}" }
                ModelAndView()
            }
        } catch (ioException: IOException) {
            logger.error { "resolver error. ${ioException.localizedMessage}" }
            null
        }
    }
}
