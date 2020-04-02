package com.peopeltech.interviewpractice.java.collection;

/**
 * 集合类相关
 * https://jackyandroid.github.io/AndroidInterview-Q-A/interview/列举java的集合和继承关系-百度-美团.html
 *
 * @author hych
 * @since 2020/4/2 22:27
 */
public abstract class Collection implements java.util.Collection {
    /**
     * {@link java.util.Iterator I}
     * ｜- {@link java.util.Collection I}
     *      |- {@link java.util.List I}
     *      |   |- {@link java.util.ArrayList}
     *      |   |- {@link java.util.LinkedList}
     *      |   |- {@link java.util.Vector}
     *      |       |- {@link java.util.Stack}
     *      |- {@link java.util.Set I}
     */

}

abstract class Map implements java.util.Map {
    /**
     * {@link Map I}
     * |
     * |- {@link java.util.HashMap}
     * |- {@link java.util.Hashtable}
     * |- {@link java.util.WeakHashMap}
     */
}
