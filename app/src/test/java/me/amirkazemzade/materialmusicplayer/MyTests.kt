package me.amirkazemzade.materialmusicplayer

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe

class MyTests : StringSpec({
    "Outer Test" should {
        true shouldBe true
        println(it)
        "Nested Test Level 1" should {
            true shouldBe true
            println(it)
            "Nested Test Level 2" {
                true shouldBe true
                println(this.testCase.name)
//                "Nested Test Level 3" {
//                    // You can write your assertions here
//                }
            }
        }
    }
})
