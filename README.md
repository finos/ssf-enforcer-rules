# Symphony Software Foundation Enforcer Rules
A collection of custom rules used to enforce Maven projects hosted by the Symphony Software Foundation.
These rules are used by the [Symphony Foundation Parent Pom](http://github.com/symphonyoss/ssf-parent-pom)

## GroupIdRule
Fails if the groupId of the current project doesn't match with groupIdPrefix (or groupIdPrefix.)
