package com.lizhuquan.concurrency.phase2.chapter07_不变模式;

/**
 *
 * 不可变设计模式, 类final, 属性final, 只暴露get方法
 * Created by lizhuquan on 2018/3/23.
 */
public final class Person {

    private final String name;

    private final Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
