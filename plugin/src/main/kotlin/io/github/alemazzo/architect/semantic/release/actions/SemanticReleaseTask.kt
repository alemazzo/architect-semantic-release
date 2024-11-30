package io.github.alemazzo.architect.semantic.release.actions

import io.github.alemazzo.architect.semantic.release.configuration.SemanticReleaseConfiguration
import io.github.alemazzo.architect.semantic.release.execution.CommandExecutor
import jakarta.inject.Singleton
import picocli.CommandLine.Command
import java.io.File

@Singleton
@Command(name = "release")
class SemanticReleaseTask(
	private val context: SemanticReleaseConfiguration,
	private val commandExecutor: CommandExecutor,
) : Runnable {

	private val command = "./release"

	private val tempDir = ".tmp"
	private val executablePath = "$tempDir/architect-semantic-release-execution"
	private val zipPath = "$executablePath.zip"
	private val url = "https://github.com/alemazzo/architect-semantic-release/releases/latest/download/semantic-release.zip"

	override fun run() {
		println("Releasing project with Semantic Release with configuration: $context")

		// Check if the tmp directory exists and if not, create it
		commandExecutor.execute("mkdir -p $tempDir")

		// Remove the plugin executable tmp directory if it exists
		commandExecutor.execute("rm -rf $executablePath")

		// Remove the zip file if it exists
		commandExecutor.execute("rm -f $zipPath")

		// Download with CURL the zip file from the latest release
		val githubTokenFromEnvironment = System.getenv("GITHUB_TOKEN")
		commandExecutor.execute("curl -H \"Authorization: token $githubTokenFromEnvironment\" --silent -L $url -o $zipPath")

		// Unzip the file
		commandExecutor.execute("unzip $zipPath -d $executablePath")

		// Remove the zip file
		commandExecutor.execute("rm $zipPath")

		// Copy the assets to the plugin executable tmp directory
		context.assets.forEach { asset ->
			println("Copying asset: ${asset.name} from ${asset.path}")
			commandExecutor.execute("cp -r ${asset.path} $executablePath/executable/${asset.name}")
		}

		// Execute the plugin
		commandExecutor.execute(command, "$executablePath/executable")

		while (File(executablePath).exists()) {
			// Remove the plugin executable tmp directory
			commandExecutor.execute("rm -rf $executablePath/executable")
			commandExecutor.execute("rm -rf $executablePath")
			val deleted = !File(executablePath).exists() && !File("$executablePath/executable").exists()
			if (deleted) {
				println("Plugin executable tmp directory removed")
				break
			}
			println("Plugin executable tmp directory not removed, waiting a second to retry")
		}

		// Remove the tmp directory if it's empty
		commandExecutor.execute("rmdir $tempDir")
	}
}
