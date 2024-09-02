package com.makers.princemaker.dto

import com.makers.princemaker.entity.Prince
import com.makers.princemaker.type.PrinceLevel
import com.makers.princemaker.type.SkillType
import lombok.*

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
