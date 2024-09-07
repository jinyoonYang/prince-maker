package com.makers.princemaker.repository

import com.makers.princemaker.entity.WoundedPrince
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author Snow
 */
@Repository
interface WoundedPrinceRepository : JpaRepository<WoundedPrince, Long>
