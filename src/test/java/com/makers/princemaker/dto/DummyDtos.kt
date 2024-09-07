package com.makers.princemaker.dto

import com.makers.princemaker.code.PrinceLevel
import com.makers.princemaker.code.SkillType
import com.makers.princemaker.controller.CreatePrince

fun dummyCreatePrinceRequest():CreatePrince.Request = CreatePrince.Request(
    princeLevel = PrinceLevel.JUNIOR_PRINCE,
    skillType = SkillType.WARRIOR,
    experienceYears = 10,
    princeId = "princeId",
    name = "name",
    age = 35
)