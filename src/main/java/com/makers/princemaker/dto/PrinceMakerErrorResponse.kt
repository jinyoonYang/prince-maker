package com.makers.princemaker.dto

import com.makers.princemaker.code.PrinceMakerErrorCode
import lombok.*

/**
 * @author Snow
 */
data class PrinceMakerErrorResponse (
    val errorCode: PrinceMakerErrorCode,
    val errorMessage: String
)
