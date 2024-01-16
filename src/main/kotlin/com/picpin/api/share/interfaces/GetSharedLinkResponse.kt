package com.picpin.api.share.interfaces

import com.fasterxml.jackson.annotation.JsonProperty

class GetSharedLinkResponse {

    data class SharedLink(
        @JsonProperty("landing_url")
        val landingUrl: String
    )
}
