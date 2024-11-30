package io.github.alemazzo.architect.semantic.release.execution

import jakarta.inject.Singleton

@Singleton
open class BashCommandExecutor : CommandExecutor {

	private fun String.splitToCommandParts(): List<String> {
		val regex = Regex("""[^\s"']+|"([^"]*)"|'([^']*)'""")
		return regex.findAll(this)
			.map { it.groupValues[0].trim('"', '\'') }
			.toList()
	}

	private fun executeCommand(command: String, workingDir: String? = null): Pair<Int, String> {
		val processBuilder = ProcessBuilder(command.splitToCommandParts())
		if (workingDir != null) {
			processBuilder.directory(java.io.File(workingDir))
		}
		val process = processBuilder.start()
		val result = process.inputStream.bufferedReader().readText()
		logBanner()
		println("************ Command output ************")
		result.lines().forEach { println(it) }
		println("******** End of architect output *********")
		logBanner()
		return Pair(process.waitFor(), result)
	}

	private fun logBanner() {
		println("************************************")
	}

	override fun execute(command: String, workingDir: String?): Boolean {
		println("Executing architect: $command in $workingDir")
		val (exitCode, result) = executeCommand(command, workingDir)
		if (exitCode != 0) {
			println("Command failed with exit code $exitCode")
			println("Command output: $result")
		}
		return exitCode == 0
	}
}