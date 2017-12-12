package com.noah.crm.cloud.utils;

import org.junit.Test;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BaseUtilTest {


    @Test
    public void testJava8() throws Exception {

        Arrays.asList("a", "b", "d").forEach(e -> System.out.println(e));
        System.out.println("-------------------");
        Arrays.asList("a", "b", "d").forEach(System.out::println);


        final Collection<Streams.Task> tasks = Arrays.asList(
                new Streams.Task(Streams.Status.OPEN, 5),
                new Streams.Task(Streams.Status.OPEN, 13),
                new Streams.Task(Streams.Status.CLOSED, 8)
        );


        // Calculate total points of all active tasks using sum()
        final long totalPointsOfOpenTasks = tasks
                .stream()
                .filter(task -> task.getStatus() == Streams.Status.OPEN)
//                .mapToInt(Streams.Task::getPoints)
                .mapToInt(task -> task.getPoints())
                .sum();

        System.out.println("Total points: " + totalPointsOfOpenTasks);

        // Calculate total points of all tasks
        final double totalPoints = tasks
                .stream()
                .parallel()
                .map(task -> task.getPoints()) // or map( Task::getPoints )
                .reduce(0, Integer::sum);

        System.out.println("Total points (all tasks): " + totalPoints);


        // Calculate the weight of each tasks (as percent of total points)
        final Collection<String> result = tasks
                .stream()                                        // Stream< String >
                .mapToInt(task -> task.getPoints())                     // IntStream
                .asLongStream()                                  // LongStream
                .mapToDouble(points -> points / totalPoints)   // DoubleStream
                .boxed()                                         // Stream< Double >
                .mapToLong(weigth -> (long) (weigth * 100)) // LongStream
                .mapToObj(percentage -> percentage + "%")      // Stream< String>
                .collect(Collectors.toList());                 // List< String >

        System.out.println(result);

    }

    @Test
    public void java8_1() throws Exception {


        // Java 8之前：
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Before Java8, too much code for too little to do");
            }
        }).start();
//Java 8方式：
        Runnable r = () -> System.out.println("In Java8, Lambda expression rocks !!");
        new Thread(r).start();
        new Thread(() -> System.out.println("In Java8, Lambda expression rocks !!")).start();


        String[] datas = new String[]{"peng", "zhao", "li"};
        Arrays.sort(datas, (v1, v2) -> Integer.compare(v1.length(), v2.length()));
        Stream.of(datas).forEach(param -> System.out.println(param));

        Consumer<Integer> c = System.out::println;
        //接口定义 括号是接口对应方法的参数列表，后面是语句和返回,括号只有一个参数时可省略
        Consumer<Integer> cc = System.out::println;
        BiConsumer<Integer, String> b = (Integer x, String y) -> System.out.println(x + " : " + y);
        Predicate<String> p = Objects::isNull;
        p.test(null);


    }


    @Test
    public void testPattern() throws Exception {
        String USERNAME_REG_EXP = "^[A-Za-z0-9_\\-\\u4e00-\\u9fa5]{2,20}$";
        Pattern USERNAME_PATTERN = Pattern.compile(USERNAME_REG_EXP);
        System.out.println(USERNAME_PATTERN.matcher("测试-*&123456"));
        System.out.println(USERNAME_PATTERN.matcher("测试-*&123456").matches());
    }

    @Test
    public void mapReduce() throws Exception {
        //Old way:
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7);
        int sum = 0;
        for(Integer n : list) {
            int x = n * n;
            sum = sum + x;
        }
        System.out.println(sum);

//New way:
        List<Integer> list11 = Arrays.asList(1,2,3,4,5,6,7);
        int sum11 = list11.stream().map(x -> x*x).reduce((x,y) -> x + y).get();
        System.out.println(sum11);
    }
}