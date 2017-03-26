# matei-bot
Experimenting with the GitHub API

This project uses the following libraries / frameworks:
- Spring Boot 1.5.2
- Lombok
- Guava

Gradle 3.4.1 is used as a build system.

Built using IntelliJ IDEA
The IntelliJ IDEA Lombok plugin has to be installed in order to avoid compilation errors while development.

Build:
$ ./gradlew build

Required environment variables to be set for running the application:
MATEIBOT_GITHUB_TOKEN=VALID_GITHB_TOKEN_VALUE

The GitHub token will be used in all requests made by the application to the GitHub API.
