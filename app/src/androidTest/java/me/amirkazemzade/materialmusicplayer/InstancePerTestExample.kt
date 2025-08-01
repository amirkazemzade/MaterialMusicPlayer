package me.amirkazemzade.materialmusicplayer

import br.com.colman.kotest.KotestRunnerAndroid
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import org.junit.runner.RunWith
import java.util.concurrent.atomic.AtomicInteger

@RunWith(KotestRunnerAndroid::class)
class InstancePerTestExample : FunSpec() {

    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerTest

    private val counter = AtomicInteger(0)

    init {
        context("a") {
            println("a=" + counter.getAndIncrement())

            val subtests = listOf("b", "c", "d")
            val subtests2 = listOf("e", "f", "g")
            withData(subtests) { testName ->
                println("$testName=" + counter.getAndIncrement())
//                withData(subtests2) {
//                    println("$it=" + counter.getAndIncrement())
//
//                }
            }
        }
    }
}