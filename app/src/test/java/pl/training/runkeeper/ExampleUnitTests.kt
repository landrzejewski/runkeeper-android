package pl.training.runkeeper

import org.hamcrest.CoreMatchers.*
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.*

import org.junit.Assert.*
import org.junit.rules.Timeout
import java.lang.IndexOutOfBoundsException
import java.util.concurrent.CountDownLatch

class ExampleUnitTests {

    private val latch = CountDownLatch(1)

    @Before
    fun beforeEach() {
        println("Before test...")
    }

    @Ignore("not ready")
    @After
    fun afterEach() {
        println("After test...")
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        assertThat(listOf(1, 2, 3), hasItems(2, 3))
        assertThat("Koltin", both(containsString("K")).and(containsString("n")))
        assertThat("123", Digits())
    }

    class Digits : TypeSafeMatcher<String>() {

        override fun describeTo(description: Description) {
            description.appendText("Should contains only digits")
        }

        override fun matchesSafely(item: String): Boolean {
            return try {
                item.toInt()
                true
            } catch (e: NumberFormatException) {
                false
            }
        }

    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun assertThrowsException() {
        listOf(2, 3)[10]
    }

    @Test
    fun shouldFail() {
        val numbers = listOf(2, 3)
        try {
            numbers[10]
            fail()
        } catch (e: IndexOutOfBoundsException) {
        }
    }

    @Test
    fun asyncTest() {
        val dataProvider = DataProvider()
        var result = ""
        dataProvider.get {
            result = it
            latch.countDown()
        }
        println("Before wait")
        latch.await()
        assertEquals("Success", result)
    }

    class DataProvider {

        fun get(callback: (String) -> Unit) {
            Thread {
                Timeout.seconds(3)
                println("After sleep")
                callback("Success")
            }.start()
        }

    }

}