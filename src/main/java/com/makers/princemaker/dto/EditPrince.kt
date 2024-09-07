package com.makers.princemaker.dto

import com.makers.princemaker.code.PrinceLevel
import com.makers.princemaker.code.SkillType

/**
 * @author Snow
 */
class EditPrince {
    data class Request (
        val princeLevel: PrinceLevel,
        val skillType: SkillType,
        val experienceYears: Int,
        val name: String,
        val age: Int
    )
}
