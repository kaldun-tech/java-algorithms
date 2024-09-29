/******************************************************************************
 *  Compilation:  javac HelloGoodbye.java
 *  Execution:    java HelloGoodbye
 *
 *  Takes two names as command-line arguments. Prints hello and goodbye
 *  messages as shown below
 *
 *  java HelloGoodbye Kevin Bob
 * Hello Kevin and Bob.
 * Goodbye Bob and Kevin.
 *
 * java HelloGoodbye Alejandra Bahati
 * Hello Alejandra and Bahati.
 * Goodbye Bahati and Alejandra.
 *
 ******************************************************************************/

import java.lang.StringBuilder;

public class HelloGoodbye {

    public static void main(String[] args) {
        String firstName = (0 < args.length) ? args[0] : "Goon1";
        String secondName = (1 < args.length) ? args[1] : "Goon2";

        StringBuilder sb = new StringBuilder("Hello ")
                .append(firstName)
                .append(" and ")
                .append(secondName)
                .append(".");
        System.out.println(sb.toString());

        sb = new StringBuilder("Goodbye ")
                .append(secondName)
                .append(" and ")
                .append(firstName)
                .append(".");
        System.out.println(sb.toString());
    }

}
