package com.makers.princemaker.entity

import com.makers.princemaker.code.PrinceLevel
import com.makers.princemaker.code.SkillType
import com.makers.princemaker.code.StatusCode
import com.makers.princemaker.code.StatusCode.*
import java.time.LocalDateTime

fun dummyPrince(
    id: Long? = null,
    princeLevel: PrinceLevel = PrinceLevel.BABY_PRINCE,
    skillType: SkillType = SkillType.WARRIOR,
    status: StatusCode = HEALTHY,
    experienceYears: Int = 1994,
    princeId: String = "intellegat",
    name: String = "Kristie Hodge",
    age: Int = 1924,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now(),
) = Prince(
    id = id,
    princeLevel = princeLevel,
    skillType = skillType,
    status = status,
    experienceYears = experienceYears,
    princeId = princeId,
    name = name,
    age = age,
    createdAt = createdAt,
    updatedAt = updatedAt
)