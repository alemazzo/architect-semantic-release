package io.github.alemazzo.architect.semantic.release

class SemanticReleaseLauncher {
	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			println("Semantic Release Launcher")
			SemanticReleaseRunner().accept(null, args.toList())
		}
	}
}