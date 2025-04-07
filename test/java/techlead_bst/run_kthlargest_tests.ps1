# Compile the Java files
javac -cp ".;c:\Git\java-algorithms\lib\junit-4.13.2.jar;c:\Git\java-algorithms\lib\hamcrest-core-1.3.jar" -d c:\Git\java-algorithms\out c:\Git\java-algorithms\src\main\java\techlead_bst\*.java c:\Git\java-algorithms\test\java\techlead_bst\KthLargestTest.java

# Run the JUnit tests
java -cp ".;c:\Git\java-algorithms\out;c:\Git\java-algorithms\lib\junit-4.13.2.jar;c:\Git\java-algorithms\lib\hamcrest-core-1.3.jar" org.junit.runner.JUnitCore techlead_bst.KthLargestTest
