package pl.training.bestweather

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart.LAZY
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.lang.Thread.sleep
import kotlin.concurrent.thread
import kotlin.coroutines.cancellation.CancellationException

class CoroutinesTest {

    @Test
    fun singleThreadRoutines() {
        println("Test started (${threadName()})")
        routine(1, 500)
        routine(2, 200)
        println("Test finished (${threadName()})")
    }

    @Test
    fun multiThreadThreadRoutines() {
        println("Test started (${threadName()})")
        newThreadRoutine(1, 500)
        newThreadRoutine(2, 200)
        println("Test finished (${threadName()})")
        sleep(1_000)
    }

    @Test
    fun lotsOfThreadsProblem() {
        repeat(10_000_000) {
            thread {
                Thread.sleep(100_000)
                println("Done")
            }
        }
    }

    @Test
    fun coroutines() = runBlocking {
        println("Test started (${threadName()})")
        launch { coroutine(1, 500) }
        launch { coroutine(2, 200) }
        println("Test finished (${threadName()})")
    }

    @Test
    fun jobs() {
        println("Test started (${threadName()})")
        GlobalScope.launch {
            coroutine(1, 500)
        }
        val secondJob = GlobalScope.launch(start = LAZY) {
            coroutine(2, 500)
        }
        //secondJob.start()
        val thirdJob = GlobalScope.launch {
            secondJob.join()
            coroutine(3, 500)
        }
        sleep(10_000)
        println("Test finished (${threadName()})")
    }

    @Test
    fun hierarchicalJobs() {
        val parentJob = GlobalScope.launch {
            delay(200)
            println("Parent job has finished (${threadName()})")
        }
        GlobalScope.launch(parentJob) {
            delay(1_000)
            println("Child job has finished (${threadName()})")
        }
        if (parentJob.children.iterator().hasNext()) {
            println("Has children ${parentJob.children.first()}")
        }
        sleep(500)
        println("Is parent job finished: ${parentJob.isCompleted}")
        sleep(10_000)
        println("Is parent job finished: ${parentJob.isCompleted}")
        println("Test has finished (${threadName()})")
    }

    @Test
    fun sharedState() {
        var isDataLoaded = false
        GlobalScope.launch {
            delay(1_000)
            isDataLoaded = true
            println("Data loading has finished (${threadName()})")
        }
        GlobalScope.launch {
            repeat(3) {
                println("Data loading status (${threadName()}): $isDataLoaded")
                delay(500)
            }
        }
        sleep(10_000)
        println("Test has finished (${threadName()})")
    }

    @Test
    fun coroutineWithResult() = runBlocking {
        val result = async(Dispatchers.IO) { getData() }
        val secondResult = getDataAsync()
        println("Finished ${result.await()}, ${secondResult.await()}")
    }

    @Test
    fun cancelingCoroutine() = runBlocking {
        println("Test started (${threadName()})")
        val job = launch {
            if (isActive) {
                println("First job starts (${threadName()})")
                try {
                    delay(10_000)
                } catch (exception: CancellationException) {
                    println("CancellationException")
                    throw exception
                }
                println("First job has finished (${threadName()})")
            }
        }
        delay(1_000)
        job.cancel()
        println("Test has finished (${threadName()})")
    }

    @Test
    fun cancelingHierarchicalJobs() = runBlocking {
        val parentJob = launch {
            delay(10_000)
            println("Parent job has finished (${threadName()})")
        }
        parentJob.invokeOnCompletion {
            if (it is CancellationException) {
                println("Parent was cancelled")
            }
        }
        val childJob = launch(parentJob) {
            delay(1_000)
            println("Child job has finished (${threadName()})")
        }
        childJob.invokeOnCompletion {
            if (it is CancellationException) {
                println("Child was cancelled")
            }
        }
        delay(500)
        //childJob.cancel()
        parentJob.cancel()
        delay(10_000)
        println("Main has finished (${threadName()})")
    }

    @Test
    fun exceptionPropagationInHierarchicalJobs() = runBlocking {
        // supervisorScope {
        val parentJob = launch {
            delay(10_000)
            println("Parent job has finished (${threadName()})")
        }
        parentJob.invokeOnCompletion {
            println("Parent isActive: $isActive ($it)")
        }
        val childJob = launch(parentJob) {
            delay(1_000)
            throw RuntimeException()
        }
        childJob.invokeOnCompletion {
            println("Child isActive: $isActive ($it)")
        }
        val secondChildJob = launch(parentJob) {
            delay(1_000)
        }
        secondChildJob.invokeOnCompletion {
            println("Second child isActive: $isActive ($it)")
        }
        delay(500)
        println("Main has finished (${threadName()})")
    }

    @Test
    fun channel() = runBlocking {
        val channel = Channel<Int>(1)
        launch(Dispatchers.Default) {
            for (value in 1 .. 10) {
                println("Producing: $value")
                channel.send(value)
            }
            channel.cancel()
        }
        for (value in channel) {
            println("Consuming: $value")
        }
    }

    fun CoroutineScope.provider() = produce {
        for (value in 1 .. 10) {
            println("Producing: $value")
            send(value)
        }
    }

    fun CoroutineScope.power(channel: ReceiveChannel<Int>) = produce {
        channel.consumeEach { send(it * it) }
    }

    @Test
    fun channelsChain() = runBlocking {
        power(provider()).consumeEach {
            println("Final value: $it")
        }
    }

    @Test
    fun tickerChannel()  = runBlocking {
        val ticker = ticker(delayMillis = 1_000, initialDelayMillis = 5_000)
        println("Before receive")
        ticker.receive()
        println("After receive")
        ticker.cancel()
    }

    fun flowProducer() = flow {
        for (value in 1 .. 10) {
            println("Producing: $value")
            emit(value)
        }
    }

    @Test
    fun flowTest() = runBlocking {
        flowProducer()
            .map { it * -1 }
            .filter { it > -3 }
            .collect { println(it) }
    }

    @Test
    fun sharedFlowTest() {
        val flow = MutableSharedFlow<Int>() // MutableStateFlow(0)
        GlobalScope.launch {
            flow.collect { println("Value: $it") }
        }
        GlobalScope.launch {
            //delay(2_000)
            println("Before emit")
            flow.emit(1)
            //delay(2_000)
            flow.emit(2)
            //delay(2_000)
            flow.emit(3)
            println("After emit")
        }
        Thread.sleep(10_000)
    }

    private suspend fun getData(): String {
        delay(5_000)
        return "Data"
    }

    private suspend fun getDataAsync(): Deferred<String> = GlobalScope.async {
        delay(5_000)
        "Data"
    }

    private suspend fun coroutine(number: Int, milliseconds: Long) {
        println("Routine $number started (${threadName()})")
        delay(milliseconds)
        println("Routine $number finished (${threadName()})")
    }

    private fun routine(number: Int, milliseconds: Long) {
        println("Routine $number started (${threadName()})")
        sleep(milliseconds)
        println("Routine $number finished (${threadName()})")
    }

    private fun newThreadRoutine(number: Int, milliseconds: Long) = thread { routine(number, milliseconds) }

    private fun threadName() = Thread.currentThread().name

}