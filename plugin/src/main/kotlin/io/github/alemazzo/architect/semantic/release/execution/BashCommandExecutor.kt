package io.github.alemazzo.architect.semantic.release.execution

import jakarta.inject.Singleton

@Singleton
open class BashCommandExecutor : CommandExecutor {

	private fun executeCommand(command: String, workingDir: String? = null): Pair<Int, String> {
		val processBuilder = ProcessBuilder(command.split(" "))
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
		val count = 1
		for (i in 0..count) {
			println("************************************")
		}
	}

	override fun execute(command: String, workingDir: String?): Boolean {
		println("Executing architect: $command in $workingDir")
		val (exitCode, _) = executeCommand(command, workingDir)
		println("Command executed with exit code $exitCode")
		return exitCode == 0
	}
}