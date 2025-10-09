# java-algorithms
Worked through the Princeton/Coursera Data Structures & Algorithms course using Java

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

### Common Gradle Commands
- `./gradlew build`: Compiles, tests, and packages your code into a JAR file
- `./gradlew tasks`: Lists all available tasks
- `./gradlew assemble`: Assembles the outputs of the project
- `./gradlew clean`: Deletes the build directory

### Running Tests

#### Run All Tests
```bash
./gradlew test
```

#### Run Tests in a Specific Package
```bash
./gradlew test --tests "leetcode.*"
```

#### Run a Specific Test Class
```bash
./gradlew test --tests "leetcode.IntegerToRomanTest"
```

#### Run a Specific Test Method
```bash
./gradlew test --tests "leetcode.IntegerToRomanTest.testBasicConversions"
```

#### Run Custom Test Tasks

##### Run LeetCode Tests
```bash
./gradlew testLeetCode
```

##### Run BST Tests
```bash
./gradlew testBST
```

### Project Structure

This project follows standard Gradle conventions:

- Implementation classes: `src/main/java/[package]/`
- Test classes: `src/test/java/[package]/`

For example:
- `src/main/java/leetcode/IntegerToRoman.java`
- `src/test/java/leetcode/IntegerToRomanTest.java`
