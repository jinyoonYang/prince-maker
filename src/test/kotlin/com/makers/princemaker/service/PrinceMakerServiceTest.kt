package com.makers.princemaker.service

import com.makers.princemaker.code.PrinceLevel
import com.makers.princemaker.code.PrinceLevel.*
import com.makers.princemaker.code.PrinceMakerErrorCode
import com.makers.princemaker.code.PrinceMakerErrorCode.DUPLICATED_PRINCE_ID
import com.makers.princemaker.code.PrinceMakerErrorCode.LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH
import com.makers.princemaker.code.SkillType
import com.makers.princemaker.code.SkillType.INTELLECTUAL
import com.makers.princemaker.code.SkillType.WARRIOR
import com.makers.princemaker.code.StatusCode
import com.makers.princemaker.code.StatusCode.*
import com.makers.princemaker.constant.PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS
import com.makers.princemaker.controller.CreatePrince
import com.makers.princemaker.dto.dummyCreatePrinceRequest
import com.makers.princemaker.entity.Prince
import com.makers.princemaker.entity.dummyPrince
import com.makers.princemaker.exception.PrinceMakerException
import com.makers.princemaker.repository.PrinceRepository
import com.makers.princemaker.repository.WoundedPrinceRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

/**
 * @author Snow
 */
@ExtendWith(MockKExtension::class)
internal class PrinceMakerServiceTest {
    @RelaxedMockK
    private lateinit var princeRepository: PrinceRepository

    @MockK
    private lateinit var woundedPrinceRepository: WoundedPrinceRepository

    @InjectMockKs
    private lateinit var princeMakerService: PrinceMakerService

    @Test
    fun princeTest(){
        //given
        val juniorPrince: Prince = dummyPrince(princeLevel = JUNIOR_PRINCE,
            skillType = INTELLECTUAL,
            experienceYears = 5)

        every{
            princeRepository.findByPrinceId(any())
        } returns juniorPrince

        //when
        val prince = princeMakerService.getPrince("princeId")

        //then
        assertEquals(JUNIOR_PRINCE, prince.princeLevel)
        assertEquals(INTELLECTUAL, prince.skillType)
        assertEquals(MAX_JUNIOR_EXPERIENCE_YEARS, prince.experienceYears)

    }

    @Test
    fun createPrinceTest_success() {
        //given
        val request: CreatePrince.Request = dummyCreatePrinceRequest().copy(experienceYears = 7,
            princeLevel = MIDDLE_PRINCE,
            skillType = INTELLECTUAL)

        every{
            princeRepository.findByPrinceId(any())
        } returns null

        every{
            princeRepository.save(any())
        } returns Prince(
            id = null,
            princeLevel = BABY_PRINCE,
            skillType = WARRIOR,
            status = HEALTHY,
            experienceYears = 2000,
            princeId = "his",
            name = "Davis Kemp",
            age = 1504,
            createdAt = null,
            updatedAt = null

        )

        val slot = slot<Prince>()
        //when
        val response = princeMakerService.createPrince(request)

        //then
        verify(exactly = 1) {
            princeRepository.save(capture(slot))
        }

        val savePrince = slot.captured
        assertEquals(MIDDLE_PRINCE, savePrince.princeLevel)
        assertEquals(INTELLECTUAL, savePrince.skillType)
        assertEquals(7, savePrince.experienceYears)

        assertEquals(MIDDLE_PRINCE, response.princeLevel)
        assertEquals(INTELLECTUAL, response.skillType)
        assertEquals(7, response.experienceYears)
    }

    @Test
    fun createPrinceTest_failed_with_duplicated() {
        //given
        val juniorPrince: Prince =
            dummyPrince(princeLevel = JUNIOR_PRINCE,
                skillType = INTELLECTUAL,
                experienceYears = MAX_JUNIOR_EXPERIENCE_YEARS,
                name = "princeId")

        val request: CreatePrince.Request = dummyCreatePrinceRequest()

        every {
            princeRepository.findByPrinceId(any())
        } returns juniorPrince

        //when
        val exception = Assertions.assertThrows(PrinceMakerException::class.java){
            princeMakerService.createPrince(request)
        }

        //then
        assertEquals(DUPLICATED_PRINCE_ID, exception.princeMakerErrorCode)
    }

    @Test
    fun createPrinceTest_failed_with_invalid_experience() {
        //given
        val request: CreatePrince.Request = dummyCreatePrinceRequest()

        every {
            princeRepository.findByPrinceId(any())
        } returns null

        //when
        var exception = Assertions.assertThrows(PrinceMakerException::class.java){
            princeMakerService.createPrince(request)
        }

        //then
        assertEquals(LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH, exception.princeMakerErrorCode)
    }
}
