package com.zy;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 流：从支持数据处理操作的源生成的元素序列
 * 集合：数据结构，主要目的是以特定的时间/空间复杂度存储和访问元素。
 * 流的目的在于表达计算。
 * 集合：数据
 * 流：计算
 *
 * 从有序集合生成流时会保留原有的顺序。由列表生成的流，其元素顺序与列表一致。
 * 流可以顺序执行，也可以并行执行。流的数据处理功能支持类似于数据库的操作。
 * 流的操作有两个重要的特点：
 * 流水线。很多流操作本身会返回一个流。这样多个流就可以链接起来，形成一个大的流水线。流水线操作可以看作对数据库进行数据库式查询。
 * 内部迭代：流的迭代操作是在背后进行的。
 * 流是以声明性的方式处理数据
 * 对流执行的操作可以分为中间操作和终端操作。中间操作的结果会返回一个流
 */
public class Main {
    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH)
        );


        List<String> threeHighCaloricDishName = menu.stream()
                .filter(dish -> dish.getClaories() >300)
                .map(Dish::getName)
                .limit(3)
                //执行collect（）方法时，才会对流进行操作。collect可以看作能够接受各种方案作为参数，并将流中的元素累积成一个汇总结果的操作
                .collect(Collectors.toList());

        System.out.println(threeHighCaloricDishName);
        long uniqueWords = 0;
        try(Stream<String> lines = Files.lines(
                Paths.get("E:\\JavaProject\\demo\\study-demos\\java-demo\\Java8\\src\\main\\resources\\test.txt"),
                Charset.defaultCharset())){
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                               .distinct()
                               .count();
            System.out.println(uniqueWords);
        } catch (IOException e){
            System.out.println("发生异常");
        }

        Stream<String> stringStream = Stream.of("Java 8", "Scala", "Kotlin");
        stringStream.map(e -> e.toUpperCase()).forEach(System.out::println);
    }
}
