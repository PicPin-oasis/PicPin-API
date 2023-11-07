package com.picpin.api.configuration

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmazonS3Configuration(
    @Value("\${aws.region}") private val region: String,
    @Value("\${aws.access_key}") private val accessKey: String,
    @Value("\${aws.secret_key}") private val secretKey: String
) {

    @Bean
    fun amazonS3(): AmazonS3 {
        val basicAWSCredentials = BasicAWSCredentials(accessKey, secretKey)
        val awsStaticCredentialsProvider = AWSStaticCredentialsProvider(basicAWSCredentials)

        return AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .withCredentials(awsStaticCredentialsProvider)
            .build()
    }
}
