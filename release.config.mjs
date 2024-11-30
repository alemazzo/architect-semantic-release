import config from 'semantic-release-preconfigured-conventional-commits' with { type: "json" };
import fs from 'fs';

const jarFiles = fs.readdirSync('./') // Read current directory
    .filter(file => file.endsWith('.jar'));
    
config.plugins.push(
    "@semantic-release/git",
    [
        "@semantic-release/github",  {
            "assets": jarFiles.map(jar => ({ path: jar })),
        }
    ],
)
export default config;
