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

const gitFolderPathRecursiveFromParent = (path = '') => {
    if (fs.existsSync(`${path}.git`)) {
        return './';
    } else {
        return gitFolderPathRecursiveFromParent(`${path}../`);
    }
}

const gitFolderPath = gitFolderPathRecursiveFromParent();

// Semantic-release configuration
export default {
    branches: ['main'], // Specify the branches for release
    plugins: [
        // Analyze commits using conventional commit rules
        ['@semantic-release/commit-analyzer', {
            preset: 'conventionalcommits',
            releaseRules: [
                {
                    type: "*!",
                    release: "major"
                },
                {
                    type: "chore",
                    scope: "api-deps",
                    release: "minor"
                },
                {
                    type: "chore",
                    scope: "core-deps",
                    release: "patch"
                },
                {
                    type: "docs",
                    release: "patch"
                },
                {
                    type: "revert",
                    release: "patch"
                }
            ],
            presetConfig: {
                types: [
                    {
                        type: "*!",
                        section: "BREAKING CHANGES"
                    },
                    {
                        type: "feat",
                        section: "Features"
                    },
                    {
                        type: "chore",
                        scope: "api-deps",
                        section: "Dependency updates"
                    },
                    {
                        type: "chore",
                        scope: "core-deps",
                        release: "patch",
                        section: "Dependency updates"
                    },
                    {
                        type: "chore",
                        scope: "deps",
                        section: "Dependency updates"
                    },
                    {
                        type: "fix",
                        section: "Bug Fixes"
                    },
                    {
                        type: "docs",
                        section: "Documentation"
                    },
                    {
                        type: "perf",
                        section: "Performance improvements"
                    },
                    {
                        type: "revert",
                        section: "Revert previous changes"
                    },
                    {
                        type: "test",
                        section: "Tests"
                    },
                    {
                        type: "ci",
                        section: "Build and continuous integration"
                    },
                    {
                        type: "build",
                        section: "Build and continuous integration"
                    },
                    {
                        type: "chore",
                        section: "General maintenance"
                    },
                    {
                        type: "style",
                        section: "Style improvements"
                    },
                    {
                        type: "refactor",
                        section: "Refactoring"
                    }
                ]}
        }],

        // Generate release notes from commits
        '@semantic-release/release-notes-generator',

        // Create GitHub releases with assets
        ['@semantic-release/github', {
            assets: assetFiles.map(AssetFile) // Attach assets to the GitHub release
        }],

        ['@semantic-release/git', {
            assets: false
            }
        ]
    ]
};