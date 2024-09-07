package com.makers.princemaker.code

enum class PrinceMakerErrorCode(
    val message: String
) {
    NO_SUCH_PRINCE("해당되는 왕자님이 안계십니다."),
    DUPLICATED_PRINCE_ID("고유 왕자번호가 중복됩니다."),
    LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH("왕자 레벨과 연차가 맞지 않습니다."),

    INTERNAL_SERVER_ERROR("서버에 오류가 발생했습니다."),
    INVALID_REQUEST("잘못된 요청입니다.")
}

enum class StatusCode (
    val description: String
){
    HEALTHY("건강한"),
    WOUNDED("부상당한")
}

enum class PrinceLevel (
    val description: String
) {
    BABY_PRINCE("아기 왕자님"),
    JUNIOR_PRINCE("어린이 왕자님"),
    MIDDLE_PRINCE("청년 왕자님"),
    KING("왕"),
    DRAGON_SLAYER("용을 무찌른 왕")
}

enum class SkillType(
    val description: String
) {
    WARRIOR("전투형"),
    INTELLECTUAL("지능형"),
    MAGE("마술사형")
}