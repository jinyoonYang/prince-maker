package com.makers.princemaker.service

import com.makers.princemaker.code.PrinceMakerErrorCode
import com.makers.princemaker.code.StatusCode
import com.makers.princemaker.constant.PrinceMakerConstant
import com.makers.princemaker.controller.CreatePrince
import com.makers.princemaker.controller.toCreatePrinceResponse
import com.makers.princemaker.dto.EditPrince
import com.makers.princemaker.dto.PrinceDetailDto
import com.makers.princemaker.dto.PrinceDto
import com.makers.princemaker.dto.toPrinceDatailDto
import com.makers.princemaker.entity.Prince
import com.makers.princemaker.entity.WoundedPrince
import com.makers.princemaker.exception.PrinceMakerException
import com.makers.princemaker.repository.PrinceRepository
import com.makers.princemaker.repository.WoundedPrinceRepository
import com.makers.princemaker.type.PrinceLevel
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

/**
 * @author Snow
 */
@Service
class PrinceMakerService (
    val princeRepository: PrinceRepository,
    val woundedPrinceRepository: WoundedPrinceRepository)
{
    @Transactional
    fun createPrince(request: CreatePrince.Request): CreatePrince.Response {
        validateCreatePrinceRequest(request)

        //Prince 생성한 뒤에 무조건 한번 호출하는 로직이 있는 경우 also를 써서 prince 객체를 만들고 바로 저장하고 그 객체를 princeResonpose 객체로 만들어서 반환한다는 걸 바로 이해할 수 있음
        //파라미터는 (T)임으로 it을 내부 호출로 써야됨
        return Prince(
            null,
            request.princeLevel!!,
            request.skillType!!,
            StatusCode.HEALTHY,
            request.experienceYears!!,
            request.princeId!!,
            request.name!!,
            request.age!!,
            null,
            null
        ).also { princeRepository.save(it)
        }.toCreatePrinceResponse()
    }

    private fun validateCreatePrinceRequest(request: CreatePrince.Request) {
        //Optional(ifPresent) -> ?.let (값이 있을때만 앞 함수에서 받은 값을 가지고 수행하는 걸 의미함)
        //findByPrinceId 값이 있는 경우에 Exception 발생
        princeRepository.findByPrinceId(request.princeId)
            ?.let { throw PrinceMakerException(PrinceMakerErrorCode.DUPLICATED_PRINCE_ID) }

        if (request.princeLevel == PrinceLevel.KING
            && request.experienceYears!! < PrinceMakerConstant.MIN_KING_EXPERIENCE_YEARS
        ) {
            throw PrinceMakerException(PrinceMakerErrorCode.LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH)
        }

        if (request.princeLevel == PrinceLevel.MIDDLE_PRINCE
            && (request.experienceYears!! > PrinceMakerConstant.MIN_KING_EXPERIENCE_YEARS
                    || request.experienceYears < PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS)
        ) {
            throw PrinceMakerException(PrinceMakerErrorCode.LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH)
        }

        if (request.princeLevel == PrinceLevel.JUNIOR_PRINCE
            && request.experienceYears!! > PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS
        ) {
            throw PrinceMakerException(PrinceMakerErrorCode.LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH)
        }
    }

    @get:Transactional
    val allPrince: List<PrinceDto>
        //map은 값이 반드시 있다는 가정하에 도는 로직임으로 파라미터를 nullable 할 필요 없음(? 제거)
        //값이 1개인 경우 it을 this 처럼 쓸 수 있음
        get() = princeRepository.findByStatusEquals(StatusCode.HEALTHY)
            .map { PrinceDto.fromEntity(it) }

    @Transactional
    fun getPrince(princeId: String): PrinceDetailDto {
        //. -> 값이 있으면, let 앞 리턴값을 가져다 씀, ?:throw = orElseThrow (optional)
        return princeRepository.findByPrinceId(princeId)
            ?.let { it.toPrinceDatailDto()}
            ?:throw PrinceMakerException(PrinceMakerErrorCode.NO_SUCH_PRINCE)
    }

    @Transactional
    fun editPrince(
        princeId: String, request: EditPrince.Request
    ): PrinceDetailDto {
        val prince = princeRepository.findByPrinceId(princeId)
            ?: throw PrinceMakerException(PrinceMakerErrorCode.NO_SUCH_PRINCE)

        //apply 여러개는 set할때 묶음으로 해당 변수의 set을 응집도 있고 묶어주는 역할을 함(함수 파라미터가 T.()임으로 내부에서 this를 써야 됨)
        prince.apply {
            this.princeLevel = request.princeLevel
            this.skillType = request.skillType
            this.experienceYears = request.experienceYears
            this.name = request.name
            this.age = request.age
        }


        return prince.toPrinceDatailDto()
    }

    @Transactional
    fun woundPrince(
        princeId: String
    ): PrinceDetailDto =
        //with은 잘 안쓰지만 굳이 쓰자면 이렇게 findByPrinceId 로 반환되는 prince를 this로 쓰면서 해당 로직이 한 묶음으로 블락을 잡아 줄 수 있음
        with(princeRepository.findByPrinceId(princeId)
            ?: throw PrinceMakerException(PrinceMakerErrorCode.NO_SUCH_PRINCE)) {

            this.status = StatusCode.WOUNDED

            WoundedPrince(
                this.id,
                this.princeId,
                this.name,
                null,
                null
            ).also {
                woundedPrinceRepository.save(it)
            }

            return this.toPrinceDatailDto()
        }
}
