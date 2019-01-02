package com.zy.demo.flink.iteration;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.streaming.api.collector.selector.OutputSelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.IterativeStream;
import org.apache.flink.streaming.api.datastream.SplitStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class IterateExample {

    private static final int BOUND = 100;

    public static void main(String[] args) throws Exception {
        //获取执行环境
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment().setBufferTimeout(1);
        //获取数据流
        DataStream<Tuple2<Integer, Integer>> inputStream = environment.addSource(new RandomFibonacciSource());
        //执行变换操作
        IterativeStream<Tuple5<Integer, Integer, Integer, Integer, Integer>> iterativeStream = inputStream.map(new InputMap())
                .iterate(5000);
        SplitStream<Tuple5<Integer, Integer, Integer, Integer, Integer>> splitStream = iterativeStream.map(new Step())
                .split(new MySelector());
        iterativeStream.closeWith(splitStream.select(MySelector.ITERATE));
        DataStream<Tuple2<Tuple2<Integer, Integer>, Integer>> numbers = splitStream.select(MySelector.OUTPUT)
                .map(new OutputMap());
        numbers.print();
        environment.execute();
        //输出操作结果

    }

    private static class RandomFibonacciSource implements SourceFunction<Tuple2<Integer, Integer>>{
        private static final long serialVersionUID = 1L;

        private Random random = new Random();

        private volatile boolean isRunning = true;
        private int counter = 0;

        @Override
        public void run(SourceContext<Tuple2<Integer, Integer>> sourceContext) throws Exception {
            while (isRunning && counter < BOUND){
                int first = random.nextInt(BOUND/2 - 1) +1;
                int second = random.nextInt(BOUND/2 - 1) +1;

                sourceContext.collect(new Tuple2<>(first, second));
                counter ++ ;
                Thread.sleep(50L);
            }
        }

        @Override
        public void cancel() {
            isRunning = false;
        }
    }

    private static class FibonacciInputMap implements MapFunction<String, Tuple2<Integer, Integer>>{

        @Override
        public Tuple2<Integer, Integer> map(String s) throws Exception {
            return null;
        }
    }

    public static class InputMap implements
            MapFunction<Tuple2<Integer, Integer>, Tuple5<Integer, Integer, Integer, Integer, Integer>>{

        @Override
        public Tuple5<Integer, Integer, Integer, Integer, Integer> map(Tuple2<Integer, Integer> value) throws Exception {
            return new Tuple5<>(value.f0, value.f1, value.f0, value.f1, 0);
        }
    }

    public static class OutputMap implements MapFunction<Tuple5<Integer, Integer, Integer, Integer, Integer>,
            Tuple2<Tuple2<Integer, Integer>, Integer>>{


        @Override
        public Tuple2<Tuple2<Integer, Integer>, Integer> map(Tuple5<Integer, Integer, Integer, Integer, Integer> value) throws Exception {
            return new Tuple2<>(new Tuple2<>(value.f0, value.f1), value.f4);
        }
    }

    public static class Step implements MapFunction<Tuple5<Integer, Integer, Integer, Integer, Integer>,
            Tuple5<Integer, Integer, Integer, Integer, Integer>>{

        @Override
        public Tuple5<Integer, Integer, Integer, Integer, Integer> map(Tuple5<Integer, Integer,
                Integer, Integer, Integer> value) throws Exception {
            return new Tuple5<>(value.f0, value.f1, value.f3, value.f2 + value.f3, ++value.f4);
        }
    }

    public static class MySelector implements OutputSelector<Tuple5<Integer, Integer, Integer, Integer, Integer>>{

        private static final String ITERATE = "iterate";
        private static final String OUTPUT = "output";

        @Override
        public Iterable<String> select(Tuple5<Integer, Integer, Integer, Integer, Integer> value) {
            List<String> output = new ArrayList<>();
            if (value.f2 < BOUND && value.f3 < BOUND){
                output.add(ITERATE);
            } else {
                output.add(OUTPUT);
            }
            return output;
        }
    }
}
