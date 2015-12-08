## lp-test

Lonely Planet project assignment

### Requirements
In order to compile and build the project, the following are required:
* Java JDK 7 or higher
* Maven 3.2.2

### Project structure
```
lp-test/
|-- pom.xml                 --> Maven pom descriptor, used for building the project
|-- README.md
|-- src/main/java           --> Contains the Java source files
|-- src/main/resources      --> Resource files such as logging configuration, HTML generator template
|                               and static HTML files used at runtime
|-- src/test/java           --> JUnit Test source files
`-- src/test/resources      --> JUnit Test resource files such as mock input XMLs and expected HTML outputs
```

### Build
The project can be built from the project's root directory with:

`$ mvn clean install -DskipTests`

`-DskipTests` will skip JUnit test execution: omit this option to execute all tests during build, or run `mvn test` manually after the first build.

### Run

To run the batch generator, from the project's root:

```
$ cd target/
$ java -jar lp-test.jar --help
```
The `--help` option will print out the tool's usage information, e.g.:
```
Usage: java -jar lp-test.jar [options]
  Options:
  * --destinationsFile, -d
       Destinations input file path
    --help, -h
       
       Default: false
  * --outputFolder, -o
       Output folder path
  * --taxonomyFile, -t
       Taxonomy input file path
```
Example usage:
`java -jar lp-test.jar -d ~/destinations.xml -t ~/taxonomy.xml -o ~/output/`



