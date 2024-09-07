package com.makers.princemaker.dto

import com.makers.princemaker.code.PrinceLevel
import com.makers.princemaker.code.SkillType
import com.makers.princemaker.code.StatusCode
import com.makers.princemaker.entity.Prince
import com.makers.princemaker.util.getLocalDateTimeString

/**
 * @author Snow
 */
data class PrinceDetailDto (
    val princeLevel: PrinceLevel,
    val skillType: SkillType,
    val experienceYears: Int,
    val princeId: String,
    val name: String,
    val age: Int,
    val status: StatusCode,
    val birthDate: String
)

fun Prince.toPrinceDatailDto() = PrinceDetailDto(
    princeLevel = this.princeLevel,
    skillType = this.skillType,
    experienceYears = this.experienceYears,
    princeId = this.princeId,
    name = this.name,
    age = this.age,
    status = this.status,
    birthDate = this.createdAt!!.getLocalDateTimeString()
)
