| NOTE! This project is not active anymore, therefore marked as ARCHIVED; if you're interested to become the maintener of this project, open an issue or email help@finos.org . |
| --- |

[![Maven Central](https://img.shields.io/maven-central/v/org.symphonyoss/ssf-enforcer-rules.svg?maxAge=2592000)](http://search.maven.org/#artifactdetails%7Corg.symphonyoss%7Cssf-enforcer-rules%7C2%7Cpom)

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


## Contributing

1. Fork it (<https://github.com/yourname/yourproject/fork>)
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Read our [contribution guidelines](.github/CONTRIBUTING.md) and [Community Code of Conduct](https://www.finos.org/code-of-conduct)
4. Commit your changes (`git commit -am 'Add some fooBar'`)
5. Push to the branch (`git push origin feature/fooBar`)
6. Create a new Pull Request

_NOTE:_ Commits and pull requests to FINOS repositories will only be accepted from those contributors with an active, executed Individual Contributor License Agreement (ICLA) with FINOS OR who are covered under an existing and active Corporate Contribution License Agreement (CCLA) executed with FINOS. Commits from individuals not covered under an ICLA or CCLA will be flagged and blocked by the FINOS Clabot tool. Please note that some CCLAs require individuals/employees to be explicitly named on the CCLA.

*Need an ICLA? Unsure if you are covered under an existing CCLA? Email [help@finos.org](mailto:help@finos.org)*


## License

Copyright 2019 - FINOS

Distributed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

SPDX-License-Identifier: [Apache-2.0](https://spdx.org/licenses/Apache-2.0)
