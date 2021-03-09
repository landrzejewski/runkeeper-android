package pl.training.runkeeper

import org.junit.Test
import org.junit.experimental.categories.Categories
import org.junit.experimental.categories.Categories.ExcludeCategory
import org.junit.experimental.categories.Categories.IncludeCategory
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

class CategoriesTests {

    @Test
    fun testA() {
        println("A")
    }

    @Category(Slow::class)
    @Test
    fun testB() {
        println("B")
    }

    @Category(Fast::class)
    @Test
    fun testC() {
        println("C")
    }

}

interface Slow
interface Fast

@RunWith(Categories::class)
@IncludeCategory(Slow::class)
@ExcludeCategory(Fast::class)
@SuiteClasses(CategoriesTests::class)
class SlowSuit