cd C:\Git\java-algorithms
javac -cp ".;C:\Git\java-algorithms\lib\algs4.jar;C:\Git\java-algorithms\lib\junit-4.13.2.jar;C:\Git\java-algorithms\lib\hamcrest-core-1.3.jar" -d bin src/main/java/techlead_bst/BinarySearchTree.java test/java/techlead_bst/BinarySearchTreeTest.java
java -cp "bin;C:\Git\java-algorithms\lib\algs4.jar;C:\Git\java-algorithms\lib\junit-4.13.2.jar;C:\Git\java-algorithms\lib\hamcrest-core-1.3.jar" org.junit.runner.JUnitCore techlead_bst.BinarySearchTreeTest
