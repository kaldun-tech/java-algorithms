# java-algorithms
Working through algorithms course using Java

## Compiling and running Java programs
Check JDK installation: `javac -version`

Compile Java file: `javac YourProgram.java`

Run the program: `java YourProgram`

### Compiling with Princeton packages

Compile with the JAR in the classpath:
`javac -cp C:\path\to\algs4.jar YourProgram.java`

Run with the JAR in the classpath:
`java -cp C:\path\to\algs4.jar YourProgram`

### Important options
Compile multiple files: `javac File1.java File2.java`

Specify output directory: `javac -d bin YourProgram.java`

Include external libraries: `javac -cp lib/dependency.jar YourProgram.java`

## Using Gradle
Set up Gradle: `gradle init`

Common Gradle commands:
gradle build: Compiles, tests, and packages your code into a JAR file

gradle tasks: Lists all available tasks

gradle assemble: Assembles the outputs of the project

gradle test: Runs the tests

gradle bootRun: For Spring Boot applications, runs the application


