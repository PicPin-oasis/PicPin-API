package com.picpin.api.verticals.interfaces

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "헬스체크 API")
@RestController
class HealthCheckApi {

    @GetMapping("/health-check")
    fun healthCheck(): ResponseEntity<Unit> = ResponseEntity.noContent().build()
}
