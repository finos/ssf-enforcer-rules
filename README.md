# Symphony Software Foundation Enforcer Rules
A collection of custom rules used to enforce Maven projects hosted by the Symphony Software Foundation.
These rules are used by the [Symphony Foundation Parent Pom](http://github.com/symphonyoss/ssf-parent-pom)

## RequireFileInArtifactRule
Fails if a given `<filename>` doesn't exist in the (JAR/WAR/ZIP) artifact built by Maven  (`${project.build.directory}/${project.artifactId}-${project.version}.${project.packaging}`); you can optionally specify a `<match>` string to be matched within the file content.

## RequireGroupIdRule
Fails if the groupId of the current project doesn't match (or start with) `<groupIdPrefix>`

## RequireMetadataRule
Fails if a given pom.xml metadata (`<metadataName>`) is not defined; you can optionally specify an `<expectedStringValue>` to validate the expected value 


