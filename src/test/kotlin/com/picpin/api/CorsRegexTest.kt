package com.picpin.api

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CorsRegexTest {

    @Test
    fun testDomainRegexMatches() {
        val regex = "\\Q\\E.*\\Q\\E".toRegex()

        assertTrue("example.com".matches(regex))
        assertTrue("subdomain.example.com".matches(regex))
        assertTrue("example.co.uk".matches(regex))
        assertTrue("localhost".matches(regex))
        assertTrue("example-company.com".matches(regex))
        assertTrue("123example.com".matches(regex))
        assertTrue("example.io".matches(regex))
        assertTrue("another.example.com/path".matches(regex))
        assertTrue("user:pass@example.com".matches(regex))
        assertTrue("example.com?query=123".matches(regex))
    }
}
