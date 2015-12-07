## lp-test

Lonely Planet project assignment

### Requirements
In order to compile and build the project, the following are required:
* Java JDK 7 or higher
* Maven 3.2.2

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



