package com.picpin.api.photo.domains.models

enum class SearchType {
    ALL, MAPPING, UN_MAPPING;

    companion object {
        fun parse(rawSearchType: String?): SearchType = SearchType.values()
            .find { it.name == rawSearchType?.uppercase() }
            ?: ALL
    }
}
