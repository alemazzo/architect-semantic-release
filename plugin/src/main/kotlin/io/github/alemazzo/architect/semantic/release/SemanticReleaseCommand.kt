package io.github.alemazzo.architect.semantic.release

import io.github.alemazzo.architect.semantic.release.actions.SemanticReleaseTask
import jakarta.inject.Singleton
import picocli.CommandLine
import picocli.CommandLine.Command

@Singleton
@Command(
	name = "semantic-release",
	subcommands = [SemanticReleaseTask::class]
)
class SemanticReleaseCommand: Runnable {
	override fun run() {
		println("Semantic Release Command")
		CommandLine.usage(this, System.out)
	}
}

