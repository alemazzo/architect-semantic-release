package io.github.alemazzo.architect.semantic.release

import io.micronaut.configuration.picocli.PicocliRunner
import jakarta.inject.Singleton

@Singleton
class SemanticReleaseLauncher {
	fun run(args: Array<String>) {
		PicocliRunner.run(SemanticReleaseCommand::class.java, *args)
	}
}

fun main(args: Array<String>) {
	SemanticReleaseLauncher().run(args)
}