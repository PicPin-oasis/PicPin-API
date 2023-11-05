package com.picpin.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PicpinApiApplication

fun main(args: Array<String>) {
	runApplication<PicpinApiApplication>(*args)
}
