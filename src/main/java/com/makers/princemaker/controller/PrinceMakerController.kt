package com.makers.princemaker.controller

import com.makers.princemaker.dto.EditPrince
import com.makers.princemaker.dto.PrinceDetailDto
import com.makers.princemaker.dto.PrinceDto
import com.makers.princemaker.service.PrinceMakerService
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * @author Snow
 */
@RestController
class PrinceMakerController (
    val princeMakerService: PrinceMakerService
) {
    @GetMapping("/princes")
    fun princes(): List<PrinceDto> {
        return princeMakerService.getAllPrince()
    }

    @GetMapping("/prince/{princeId}")
    fun getPrince(
        @PathVariable princeId: String
    ): PrinceDetailDto {
        return princeMakerService.getPrince(princeId)
    }

    @PutMapping("/prince/{princeId}")
    fun updatePrince(
        @PathVariable princeId: String,
        @Valid
        @RequestBody request: EditPrince.Request
    ): PrinceDetailDto {
        return princeMakerService.editPrince(princeId, request)
    }

    @DeleteMapping("/prince/{princeId}")
    fun deletePrince(
        @PathVariable princeId: String
    ): PrinceDetailDto {
        return princeMakerService.woundPrince(princeId)
    }
}
