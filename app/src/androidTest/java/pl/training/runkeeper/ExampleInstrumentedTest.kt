package pl.training.runkeeper

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("pl.training.runkeeper", appContext.packageName)
    }

    @Test
    fun changeText_sameActivity() {
        onView(withId(R.id.forecast_city_name))
            .perform(replaceText("Warsaw"), ViewActions.closeSoftKeyboard())
        Thread.sleep(1_000)
        onView(withId(R.id.forecast_list))
            .check(RecyclerViewCountAssertion(7))
    }

}

class RecyclerViewCountAssertion(private val itemsCount: Int) : ViewAssertion {

    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null){
            throw noViewFoundException
        }
        val recycler = view as RecyclerView
        val adapter = recycler.adapter
        assertEquals(itemsCount, adapter?.itemCount ?: 0)
    }

}