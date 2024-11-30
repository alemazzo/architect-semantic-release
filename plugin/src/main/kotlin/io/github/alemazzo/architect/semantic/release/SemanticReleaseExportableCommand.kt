package io.github.alemazzo.architect.semantic.release

import jakarta.inject.Singleton
import picocli.CommandLine

interface PluggableCommand {
	val name: String
	fun run(args: List<String> = emptyList())
}

@Singleton
class SemanticReleaseExportableCommand(
	private val commandLine: CommandLine
): PluggableCommand {
	override val name: String = "semantic-release"

	override fun run(args: List<String>) {
		commandLine.execute(*args.toTypedArray())
	}
}