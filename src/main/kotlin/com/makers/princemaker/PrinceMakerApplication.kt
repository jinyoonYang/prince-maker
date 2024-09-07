package com.makers.princemaker

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class PrinceMakerApplication
//{
//    companion object {
//        @JvmStatic
//        fun main(args: Array<String>) {
//            SpringApplication.run(PrinceMakerApplication::class.java, *args)
//        }
//    }
//}

fun main(args: Array<String>) {
    SpringApplication.run(PrinceMakerApplication::class.java, *args)
}
