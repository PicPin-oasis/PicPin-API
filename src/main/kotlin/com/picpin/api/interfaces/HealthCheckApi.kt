package com.picpin.api.interfaces

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckApi {

    @GetMapping("/health-check")
    fun healthCheck(): ResponseEntity<Unit> = ResponseEntity.noContent().build()
}
