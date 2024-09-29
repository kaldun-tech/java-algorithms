/******************************************************************************
 *
 *  Reads a sequence of words from standard input and prints those
 *  uniformly at random. Do not store the words in an array or list.
 *  Instead use Knuth's method: when reading the i'th word, select
 *  it with probability 1 / i to be the champion, replacing the previous
 *  champion. After reading all words print the surviving champion.
 *
 *  Compile: javac-algs4 RandomWord.java
 *
 *  Run:
 *  java-algs4 RandomWord
 *  heads tails
 *  tails
 *
 *  more animals8.txt
 *  ant bear cat dog
 *  emu fox goat horse
 *
 *  java-algs4 RandomWord < animals8.txt
 *  emu
 */