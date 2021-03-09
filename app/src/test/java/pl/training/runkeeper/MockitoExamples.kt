package pl.training.runkeeper

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.lang.IllegalStateException

//@RunWith(MockitoJUnitRunner::class)
class MockitoExamples {

    //@Mock
    lateinit var mockedList: MutableList<Int>

    @Before
    fun setup() {
        //MockitoAnnotations.openMocks(MockitoExamples::class)
        mockedList = mock()
    }

    @Test
    fun test() {
        //`when`(mockedList[0]).thenReturn(1)
        //`when`(mockedList[0]).thenReturn(2)
        whenever(mockedList[0]).thenReturn(2)

        `when`(mockedList[anyInt()]).thenReturn(1)
        doThrow(IllegalStateException::class.java).`when`(mockedList.clear())

        println(mockedList[0])

        verify(mockedList).add(1)
        verify(mockedList)[anyInt()]
        verify(mockedList, times(2))[anyInt()]
        verify(mockedList, atMost(2))[anyInt()]
        verifyNoInteractions(mockedList)
    }

}