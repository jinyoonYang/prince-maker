package com.makers.princemaker.exception

import com.makers.princemaker.code.PrinceMakerErrorCode
import lombok.Getter

/**
 * @author Snow
 */
@Getter
class PrinceMakerException : RuntimeException {
    var princeMakerErrorCode: PrinceMakerErrorCode
    var detailMessage: String

    constructor(princeMakerErrorCode: PrinceMakerErrorCode) : super(princeMakerErrorCode.message) {
        this.princeMakerErrorCode = princeMakerErrorCode
        this.detailMessage = princeMakerErrorCode.message
    }

    constructor(
        princeMakerErrorCode: PrinceMakerErrorCode,
        detailMessage: String
    ) : super(detailMessage) {
        this.princeMakerErrorCode = princeMakerErrorCode
        this.detailMessage = detailMessage
    }
}
