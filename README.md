# Symphony Software Foundation Enforcer Rules
A collection of custom rules used to enforce Maven projects hosted by the Symphony Software Foundation.
These rules are used by the [Symphony Foundation Parent Pom](http://github.com/symphonyoss/ssf-parent-pom)

Usage:
```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-enforcer-plugin</artifactId>
    <configuration>
      <rules>
        <myCustomRule implementation="org.symphonyoss.maven.enforcer.RequireGroupIdRule">
          <groupIdPrefix>org.symphonyoss</groupIdPrefix>
        </myCustomRule>
        <myCustomRule implementation="org.symphonyoss.maven.enforcer.RequireFileInArtifactRule">
          <fileName>NOTICE</fileName>
          <match>The Symphony Software Foundation (http://symphony.foundation)</match>
        </myCustomRule>
        <myCustomRule implementation="org.symphonyoss.maven.enforcer.RequireFileInArtifactRule">
          <fileName>LICENSE</fileName>
          <match>Copyright 2016 The Symphony Software Foundation</match>
        </myCustomRule>
        <myCustomRule implementation="org.symphonyoss.maven.enforcer.RequirePomMetadataRule"/>
      </rules>
    </configuration>
</plugin>

```

## RequireFileInArtifactRule
Fails if a given `<filename>` doesn't exist in the (JAR/WAR/ZIP) artifact built by Maven  (`${project.build.directory}/${project.artifactId}-${project.version}.${project.packaging}`); you can optionally specify a `<match>` string to be matched within the file content.

## RequireGroupIdRule
Fails if the groupId of the current project doesn't match (or start with) `<groupIdPrefix>`

## RequirePomMetadataRule
Fails if the Maven Central requirements for publishing are not respected by the root pom.xml of the current project; the elements checked are:
- `<url>`
- `<description>`
- `<scm>`
- `<developers>`
- `<licenses>`