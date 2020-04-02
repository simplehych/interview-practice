package com.peopeltech.interviewpractice.java;

/**
 * 抽象类和接口
 * <p>
 * https://jackyandroid.github.io/AndroidInterview-Q-A/interview/抽象类和接口-360.html
 *
 * @author hych
 * @since 2020/4/2 22:16
 */
interface InterfaceListener extends Iterable { // 接口可以继承

    /**
     * 接口 - 声明约束规范
     */
    void standard();

    /**
     * 接口 - 扩展功能
     *
     * @return 返回值
     */
    int extension();

    /**
     * 接口 - 监听功能
     */
    void listener();

    /**
     * java8 功能
     * 不用实现类强制
     */
    default void eightFeature() {

    }
}
