package io.github.alemazzo.architect.semantic.release

import io.micronaut.configuration.picocli.PicocliRunner
import jakarta.inject.Singleton

@Singleton
object SemanticReleaseLauncher {
	@JvmStatic
	fun main(args: Array<String>) {
		PicocliRunner.run(SemanticReleaseCommand::class.java, *args)
	}
}