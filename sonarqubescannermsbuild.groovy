#!./lib/runner.groovy
// Generates server-side metadata for MSBuild SonarQube Runner
import net.sf.json.*

def url = "https://api.github.com/repos/SonarSource-VisualStudio/sonar-msbuild-runner/releases".toURL()
def releases = JSONArray.fromObject(url.text)

def json = []

for (JSONObject release : releases) {
  if (!release.get("draft") && !release.get("prerelease")) {
    json << ["id": release.get("tag_name"),
             "name": "SonarQube Scanner for MSBuild ${release.get("tag_name")}".toString(), 
             "url": "https://github.com/SonarSource-VisualStudio/sonar-msbuild-runner/releases/download/${release.get("tag_name")}/MSBuild.SonarQube.Runner-${release.get("tag_name")}.zip".toString()];
  }
}

lib.DataWriter.write("hudson.plugins.sonar.MsBuildSonarQubeRunnerInstaller",JSONObject.fromObject([list:json]));

