package com.makers.princemaker.dto

import com.makers.princemaker.code.PrinceLevel
import com.makers.princemaker.code.SkillType
import com.makers.princemaker.entity.Prince

/**
 * @author Snow
 */
data class PrinceDto (
    val princeLevel: PrinceLevel? = null,
    val skillType: SkillType? = null,
    val princeId: String? = null
){
    companion object {
        fun fromEntity(prince: Prince?): PrinceDto {
            if(null == prince){
                return PrinceDto()
            }

            return PrinceDto(
                princeLevel = prince.princeLevel,
                skillType = prince.skillType,
                princeId = prince.princeId
            )
        }
    }
}
