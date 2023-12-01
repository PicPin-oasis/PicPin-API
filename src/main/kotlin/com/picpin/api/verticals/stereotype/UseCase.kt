package com.picpin.api.verticals.stereotype

import org.springframework.stereotype.Service

@Service
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class UseCase()
