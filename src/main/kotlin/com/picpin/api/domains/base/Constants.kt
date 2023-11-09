package com.picpin.api.domains.base

import java.time.ZoneId

val DEFAULT_ZONE_ID: ZoneId = ZoneId.of("Asia/Seoul")
const val HEX_CODE_REGEX = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$"
