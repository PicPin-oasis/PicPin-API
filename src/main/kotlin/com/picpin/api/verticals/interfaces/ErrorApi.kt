package com.picpin.api.verticals.interfaces

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Hidden
@RestController
class ErrorApi : ErrorController {

    @GetMapping("/error")
    fun error(): ResponseEntity<String> {
        return ResponseEntity.internalServerError().body("oops! something went wrong.")
    }
}
