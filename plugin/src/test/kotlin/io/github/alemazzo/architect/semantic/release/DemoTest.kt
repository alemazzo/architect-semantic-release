package io.github.alemazzo.architect.semantic.release
import io.micronaut.context.ApplicationContext
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import jakarta.inject.Inject

@MicronautTest
class DemoTest {

    @Inject
    lateinit var application: ApplicationContext

    @Test
    fun testItWorks() {
        Assertions.assertTrue(application.isRunning)
    }

}
