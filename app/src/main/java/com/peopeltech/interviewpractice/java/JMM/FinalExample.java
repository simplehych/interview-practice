package com.peopeltech.interviewpractice.java.JMM;

/**
 * @author hych
 * @since 2020/4/6 09:43
 */
public class FinalExample {

    int i;
    final int j;
    static FinalExample obj;

    public FinalExample() {
        i = 1;
        j = 2;
    }

    public static void writer() {
        obj = new FinalExample();
    }

    public static void reader() {
        FinalExample object = obj;
        int a = object.i;
        int b = object.j;
    }
}
