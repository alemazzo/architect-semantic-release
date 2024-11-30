package io.github.alemazzo.architect.semantic.release

import io.micronaut.configuration.picocli.PicocliRunner
import jakarta.inject.Singleton

interface MainCommand {
	fun run(args: List<String>)
}

@Singleton
class SemanticReleaseLauncher: MainCommand {
	override fun run(args: List<String>) {
		PicocliRunner.run(SemanticReleaseCommand::class.java, *args.toTypedArray())
	}
}

fun main(args: Array<String>) {
	SemanticReleaseLauncher().run(args.toList())
}