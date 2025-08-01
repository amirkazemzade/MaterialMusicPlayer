package me.amirkazemzade.materialmusicplayer

import br.com.colman.kotest.KotestRunnerAndroid
import io.kotest.core.spec.style.FunSpec
import org.junit.runner.RunWith

@RunWith(KotestRunnerAndroid::class)
class MyTests : FunSpec({

    context("a") {
        beforeAny {
            println(this.testCase.name.testName)
        }
        context("b") {
            beforeAny {
                println(this.testCase.name.testName)
            }
            test("c") {
                println(this.testCase.name.testName)
            }
        }
    }
})
