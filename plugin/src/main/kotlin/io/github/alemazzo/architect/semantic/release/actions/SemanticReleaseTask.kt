package io.github.alemazzo.architect.semantic.release.actions

import io.github.alemazzo.architect.semantic.release.configuration.SemanticReleaseConfiguration
import io.github.alemazzo.architect.semantic.release.execution.CommandExecutor
import jakarta.inject.Singleton
import picocli.CommandLine.Command

@Singleton
@Command(name = "release")
class SemanticReleaseTask(
	private val context: SemanticReleaseConfiguration,
	private val commandExecutor: CommandExecutor,
) : Runnable {

	private val command = "./release"

	private val tempDir = ".architect/tmp"
	private val executablePath = "$tempDir/architect-semantic-release-execution"
	private val zipPath = "$executablePath.zip"
	private val url = "https://github.com/alemazzo/architect-semantic-release/releases/latest/download/architect-semantic-release.zip"

	override fun run() {
		println("Releasing project with Semantic Release")

		// Check if the tmp directory exists and if not, create it
		commandExecutor.execute("mkdir -p .architect/tmp")

		// Check if the plugin executable tmp directory exists and if so, remove it
		commandExecutor.execute("rm -rf $executablePath")

		// Download with CURL the zip file from the latest release
		commandExecutor.execute("curl -LJO $url -o $zipPath")

		// Unzip the file
		commandExecutor.execute("unzip $zipPath -d $executablePath")

		// Remove the zip file
		commandExecutor.execute("rm $zipPath")

		// Copy the assets to the plugin executable tmp directory
		context.assets.forEach { asset ->
			commandExecutor.execute("cp -r ${asset.path} $executablePath/${asset.name}")
		}

		// Execute the plugin
		commandExecutor.execute(command, executablePath)

		// Remove the plugin executable tmp directory
		commandExecutor.execute("rm -rf $executablePath")

		// Remove the tmp directory if it's empty
		commandExecutor.execute("rmdir $tempDir")
	}
}
