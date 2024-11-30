package io.github.alemazzo.architect.semantic.release

import io.micronaut.configuration.picocli.MicronautFactory
import io.micronaut.context.ApplicationContext
import jakarta.inject.Singleton
import picocli.CommandLine
import java.util.function.BiConsumer


@Singleton
class SemanticReleaseRunner: BiConsumer<ApplicationContext?, List<String>> {
	override fun accept(applicationContext: ApplicationContext?, args: List<String>) {
		println("Semantic Release Runner with context: $applicationContext and args: $args")
		val factory = if (applicationContext == null) MicronautFactory() else MicronautFactory(applicationContext)
		val commandLine = CommandLine(SemanticReleaseCommand(), factory)
		commandLine.execute(*args.toTypedArray())
	}
}