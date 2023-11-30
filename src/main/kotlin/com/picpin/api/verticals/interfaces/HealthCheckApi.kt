package com.picpin.api.verticals.interfaces

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Hidden
@RestController
class HealthCheckApi {

    @GetMapping("/health-check")
    fun healthCheck(): ResponseEntity<Unit> = ResponseEntity.noContent().build()
}
