import config from 'semantic-release-preconfigured-conventional-commits' with { type: "json" };
import fs from 'fs';

const allFiles = fs.readdirSync('./') // Read current directory
const requiredFiles = ['package.json', 'package-lock.json', 'release.config.mjs', 'release'];
const ignoredFiles = ['.gitignore', 'README.md', 'node_modules', 'CHANGELOG.md', 'default.DS_Store', '.DS_Store']
const assetFiles = allFiles.filter(file => !requiredFiles.includes(file) && !ignoredFiles.includes(file));

function AssetFile(path){
    return {
        path: path
    }
}

config.plugins = [
    [
        "@semantic-release/commit-analyzer", {
            "preset": "conventionalcommits",
            "presetConfig": {
                "types": "*types"
            },
        }
    ],
    "@semantic-release/git",
    [
        "@semantic-release/github",  {
            "assets": assetFiles.map(AssetFile)
        }
    ]
]

export default config;
