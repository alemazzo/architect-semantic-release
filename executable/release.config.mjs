import fs from 'fs';

// Identify asset files based on the given logic
const allFiles = fs.readdirSync('./'); // Read current directory
const requiredFiles = ['package.json', 'package-lock.json', 'release.config.mjs', 'release'];
const ignoredFiles = ['.gitignore', 'README.md', 'node_modules', 'CHANGELOG.md', 'default.DS_Store', '.DS_Store'];
const assetFiles = allFiles.filter(file => !requiredFiles.includes(file) && !ignoredFiles.includes(file));

// Helper function to format asset file paths
function AssetFile(path) {
    return { path };
}

// Semantic-release configuration
export default {
    branches: ['main'], // Specify the branches for release
    plugins: [
        // Analyze commits using conventional commit rules
        ['@semantic-release/commit-analyzer', { preset: 'conventionalcommits' }],

        // Generate release notes from commits
        '@semantic-release/release-notes-generator',

        // Generate/update the CHANGELOG.md file
        // ['@semantic-release/changelog', {
        //    changelogFile: 'CHANGELOG.md'
        // }],

        // Create GitHub releases with assets
        ['@semantic-release/github', {
            assets: assetFiles.map(AssetFile) // Attach assets to the GitHub release
        }],

        // Commit updated files (e.g., CHANGELOG.md)
        ['@semantic-release/git', {
            assets: [],
            message: 'chore(release): ${nextRelease.version} [skip ci]\n\n${nextRelease.notes}'
        }]
    ]
};