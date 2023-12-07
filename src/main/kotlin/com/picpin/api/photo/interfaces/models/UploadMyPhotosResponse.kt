package com.picpin.api.photo.interfaces.models

import com.fasterxml.jackson.annotation.JsonProperty

data class UploadMyPhotosResponse(
    @JsonProperty("upload_file_paths")
    val uploadFilePaths: List<String>,
    @JsonProperty("failed_upload_filenames")
    val failedUploadFilenames: List<String>
)
