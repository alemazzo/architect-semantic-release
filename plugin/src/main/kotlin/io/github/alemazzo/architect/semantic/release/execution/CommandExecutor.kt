package io.github.alemazzo.architect.semantic.release.execution


interface CommandExecutor {
	fun execute(command: String, workingDir: String? = null): Boolean
}

