package com.makers.princemaker.dto

import com.makers.princemaker.code.StatusCode
import com.makers.princemaker.entity.Prince
import com.makers.princemaker.type.PrinceLevel
import com.makers.princemaker.type.SkillType
import com.makers.princemaker.util.getLocalDateTimeString

/**
 * @author Snow
 */
data class PrinceDetailDto (
    val princeLevel: PrinceLevel? = null,
    val skillType: SkillType? = null,
    val experienceYears: Int? = null,
    val princeId: String? = null,
    val name: String? = null,
    val age: Int? = null,
    val status: StatusCode? = null,
    val birthDate: String? = null
){
    companion object {
        @JvmStatic
        fun fromEntity(prince: Prince?): PrinceDetailDto {
            if(null == prince){
                return PrinceDetailDto()
            }

            return PrinceDetailDto(
                princeLevel = prince.princeLevel,
                skillType = prince.skillType,
                experienceYears = prince.experienceYears,
                princeId = prince.princeId,
                name = prince.name,
                age = prince.age,
                status = prince.status,
                birthDate = getLocalDateTimeString(prince.createdAt))
        }
    }
}
