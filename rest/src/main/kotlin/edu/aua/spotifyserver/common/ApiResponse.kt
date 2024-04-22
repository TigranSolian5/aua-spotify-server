package edu.aua.spotifyserver.common

import com.fasterxml.jackson.annotation.JsonInclude

enum class ResponseStatus {
    success, failure;
}

@JsonInclude(JsonInclude.Include.NON_NULL)
class ResponseMetadata(
    val nextPageUrl: String? = null,
    val errorMessage: String? = null,
)

abstract class AbstractResponse<T>(
    val status: ResponseStatus,
    val response: T,
    val metadata: ResponseMetadata,
)

class SuccessResponse<T>(
    response: T,
    metadata: ResponseMetadata,
) : AbstractResponse<T>(
    ResponseStatus.success,
    response,
    metadata,
)

class FailureResponse(
    reason: String,
    metadata: ResponseMetadata,
): AbstractResponse<String>(
    ResponseStatus.failure,
    reason,
    metadata,
)
