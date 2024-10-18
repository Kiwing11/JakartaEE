# Jakarta EE Laboratories

[![MIT licensed][shield-mit]](LICENSE)
[![Java v17][shield-java]](https://openjdk.java.net/projects/jdk/17/)
[![Jakarta EE v10][shield-jakarta]](https://jakarta.ee/specifications/platform/10/)

## Requirements

The list of tools required to build and run the project:

- Open JDK 17
- Apache Maven 3.8

## Building

In order to build project use:

```bash
mvn clean package
```

If your default `java` is not from JDK 17 use (in `knife-store` directory):

```bash
JAVA_HOME=<path_to_jdk_home> mvn package
```

## Running

In order to run using Open Liberty Application server use (in `knife-store` directory):

```bash
mvn -P liberty liberty:dev
```

If your default `java` is not from JDK 17 use (in `knife-store` directory):

```bash
JAVA_HOME=<path_to_jdk_home> mvn -P liberty liberty:dev
```

## License

Project is licensed under the [MIT](LICENSE) license.

[shield-mit]: https://img.shields.io/badge/license-MIT-blue.svg
[shield-java]: https://img.shields.io/badge/Java-17-blue.svg
[shield-jakarta]: https://img.shields.io/badge/Jakarta_EE-10-blue.svg
