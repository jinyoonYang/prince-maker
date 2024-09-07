package com.makers.princemaker.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.makers.princemaker.code.PrinceLevel
import com.makers.princemaker.code.PrinceMakerErrorCode
import com.makers.princemaker.code.SkillType
import com.makers.princemaker.dto.PrinceDto
import com.makers.princemaker.exception.PrinceMakerException
import com.makers.princemaker.repository.PrinceRepository
import com.makers.princemaker.repository.WoundedPrinceRepository
import com.makers.princemaker.service.PrinceMakerService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.nio.charset.StandardCharsets
import java.util.*

/**
 * @author Snow
 */
@WebMvcTest(controllers = [PrinceMakerController::class, CreatePrinceController::class])
@MockkBean(JpaMetamodelMappingContext::class)
internal class PrinceMakerControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var princeMakerService: PrinceMakerService

    val objectMapper = ObjectMapper()

    val contentType: MediaType = MediaType(
        MediaType.APPLICATION_JSON.type,
        MediaType.APPLICATION_JSON.subtype,
        StandardCharsets.UTF_8
    )

    @Throws(Exception::class)
    @Test
    fun allPrince() {
        //given
        val warriorKing = PrinceDto(
            PrinceLevel.KING,
            SkillType.WARRIOR,
            "princeId"
        )

        val intellectualJuniorPrince = PrinceDto(
            PrinceLevel.JUNIOR_PRINCE,
            SkillType.INTELLECTUAL,
            "princeId2"
        )

        //Mockito 버전 (@MockBean)
//        `when`(princeMakerService.getAllPrince()).thenReturn(Arrays.asList(warriorKing, intellectualJuniorPrince))
        every{
            princeMakerService.getAllPrince()
        } returns Arrays.asList(warriorKing, intellectualJuniorPrince)

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/princes").contentType(contentType))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.jsonPath(
                    "$.[0].skillType",
                    CoreMatchers.`is`(SkillType.WARRIOR.name)
                )
            )
            .andExpect(
                MockMvcResultMatchers.jsonPath(
                    "$.[0].princeLevel",
                    CoreMatchers.`is`(PrinceLevel.KING.name)
                )
            )
            .andExpect(
                MockMvcResultMatchers.jsonPath(
                    "$.[1].skillType",
                    CoreMatchers.`is`(SkillType.INTELLECTUAL.name)
                )
            )
            .andExpect(
                MockMvcResultMatchers.jsonPath(
                    "$.[1].princeLevel",
                    CoreMatchers.`is`(PrinceLevel.JUNIOR_PRINCE.name)
                )
            )
    }

    @Test
    @Throws(Exception::class)
    fun createPrinceSuccess() {
        //given
        val createPrinceRequest = CreatePrince.Request(
            PrinceLevel.BABY_PRINCE,
            SkillType.INTELLECTUAL,
            20,
            "princeId",
            "name",
            20
        )
        every {  princeMakerService.createPrince(any())
        } returns CreatePrince.Response(
                PrinceLevel.BABY_PRINCE,
                SkillType.INTELLECTUAL,
                20,
                "princeId",
                "name",
                20
            )

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/create-prince")
                .contentType(contentType)
                .content(objectMapper.writeValueAsString(createPrinceRequest))
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(
                MockMvcResultMatchers.jsonPath(
                    "$.skillType",
                    CoreMatchers.`is`(SkillType.INTELLECTUAL.name)
                )
            )
            .andExpect(
                MockMvcResultMatchers.jsonPath(
                    "$.princeLevel",
                    CoreMatchers.`is`(PrinceLevel.BABY_PRINCE.name)
                )
            )
    }

    @Test
    @Throws(Exception::class)
    fun createPrinceFailed() {
        //given
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/create-prince")
                .contentType(contentType)
                .content(
                    "{\"princeLevel\":\"BABY_PRINCE\",\"skillType\":\"INTELLECTUAL\",\"princeId\":\"princeId\",\"name\":\"name\",\"age\":20}"
                )
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(
                MockMvcResultMatchers.jsonPath(
                    "$.errorCode",
                    CoreMatchers.`is`(PrinceMakerErrorCode.INVALID_REQUEST.name)
                )
            )
            .andExpect(
                MockMvcResultMatchers.jsonPath(
                    "$.errorMessage",
                    CoreMatchers.`is`(PrinceMakerErrorCode.INVALID_REQUEST.message)
                )
            )
    }

    @Test
    @Throws(Exception::class)
    fun testErrorMessage() {
        //given
       every { princeMakerService.getAllPrince() } throws  PrinceMakerException(PrinceMakerErrorCode.NO_SUCH_PRINCE)

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/princes").contentType(contentType))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.jsonPath(
                    "$.errorCode",
                    CoreMatchers.`is`(PrinceMakerErrorCode.NO_SUCH_PRINCE.name)
                )
            )
    }
}

