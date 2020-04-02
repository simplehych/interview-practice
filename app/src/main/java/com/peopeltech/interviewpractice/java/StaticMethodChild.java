package com.peopeltech.interviewpractice.java;

/**
 * @author hych
 * @since 2020/4/2 22:09
 */
public class StaticMethodChild extends StaticMethodParent {

    public static void main(String[] args) {
        StaticMethodParent.test();
        StaticMethodChild.test();
    }

    static void test() {
        System.out.println("StaticMethodChild test");
    }
}
