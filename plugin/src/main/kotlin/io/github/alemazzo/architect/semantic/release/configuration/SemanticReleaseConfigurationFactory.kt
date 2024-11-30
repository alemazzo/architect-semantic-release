package io.github.alemazzo.architect.semantic.release.configuration

import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton
import java.io.File

@Singleton
@Factory
class SemanticReleaseConfigurationFactory{

	private val defaultConfigurationPath = ".architect/semantic-release.yml"

	@Singleton
	fun getSemanticReleaseConfiguration(configurationParser: ConfigurationParser): SemanticReleaseConfiguration {
		val file = File(defaultConfigurationPath)
		if (!file.exists()) {
			println("No semantic release configuration found")
			return SemanticReleaseConfiguration()
		}
		val content = file.readText()
		val parsed = configurationParser.parse<SemanticReleaseConfiguration>(content)
		if (parsed == null) {
			println("Error parsing semantic release configuration")
			return SemanticReleaseConfiguration()
		}
		return parsed
	}

}