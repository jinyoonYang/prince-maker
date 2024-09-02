package com.makers.princemaker.dto

import com.makers.princemaker.entity.Prince
import com.makers.princemaker.type.PrinceLevel
import com.makers.princemaker.type.SkillType
import lombok.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 * @author Snow
 */
class CreatePrince {
    data class Request(
        @field:NotNull
        val princeLevel: PrinceLevel? = null,
        @field:NotNull
        val skillType: SkillType? = null,
        @field:NotNull
        @field:Min(0)
        val experienceYears: Int? = null,
        @field:NotNull
        @field:Size(min = 3, max = 50, message = "invalid princeId")
        val princeId: String? = null,
        @field:NotNull
        @field:Size(min = 2, max = 50, message = "invalid name")
        val name: String? = null,
        @field:NotNull @Min(18)
        val age: Int? = null
    )

    data class Response(
        val princeLevel: PrinceLevel? = null,
        val skillType: SkillType? = null,
        val experienceYears: Int? = null,
        val princeId: String? = null,
        val name: String? = null,
        val age: Int? = null
    ) {
        companion object {
            @JvmStatic
            fun fromEntity(prince: Prince): Response {
                //코틀린에선 빌더 필요 없음 (기본 생성자의 파라미터 명을 직접 지정할 수 있기 때문에)
                return Response(
                    princeLevel = prince.princeLevel,
                    skillType = prince.skillType,
                    experienceYears = prince.experienceYears,
                    princeId = prince.princeId,
                    name = prince.name,
                    age = prince.age)
            }
        }
    }
}
