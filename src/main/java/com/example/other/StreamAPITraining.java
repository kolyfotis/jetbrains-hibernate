package com.example.other;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamAPITraining {
    public static void main(String[] args) {

//        *** STREAMS ***

//        filter
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        List<String> nonEmptyStrings = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        int emptyCount = (int) strings.stream().filter(string -> string.isEmpty()).count();

//        forEach
        strings.forEach(System.out::println);
        System.out.println("---");
        nonEmptyStrings.forEach(System.out::println);
        System.out.println("---");
        System.out.println("Number of empty: " + emptyCount);
        System.out.println("---");

//        map
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
//        distinct will make sure the values in the list are unique
        List<Integer> squaresList = numbers.stream().map(i -> i * i).distinct().collect(Collectors.toList());

        numbers.forEach(System.out::println);
        System.out.println("---");
        squaresList.forEach(System.out::println);
        System.out.println("---");

//        limit
        Random random = new Random();
        random.ints(50, 0, 100).limit(5).forEach(System.out::println);
        System.out.println("---");

//        sorted
        random.ints(0, 100).limit(5).sorted().forEach(System.out::println);

//        Collectors
//        List collector
        List<String> filtered = strings.stream().filter(str -> !str.isEmpty()).collect(Collectors.toList());
        System.out.println("Filtered List: " + filtered);
        System.out.println("---");

//        String collector
        String mergedString = strings.stream().filter(str -> !str.isEmpty()).collect(Collectors.joining(", "));
        System.out.println("Merged String: " + mergedString);
        System.out.println("---");

//        Statistics
        IntSummaryStatistics stats = numbers.stream().mapToInt(x -> x).summaryStatistics();

        System.out.println("Highest number in List: " + stats.getMax());
        System.out.println("Lowest number in List: " + stats.getMin());
        System.out.println("Sum of all numbers: " + stats.getSum());
        System.out.println("Average of all numbers: " + stats.getAverage());
        System.out.println("---");


//        *** PARALLEL STREAMS ***

        emptyCount = (int) strings.parallelStream().filter(string -> string.isEmpty()).count();
        System.out.println("Number of empty: " + emptyCount);
        System.out.println("---");

        List<Integer> listOfNumbers = Arrays.asList(1, 2, 3, 4);
        listOfNumbers.parallelStream().forEach(num ->
                System.out.println(num + " " + Thread.currentThread().getName()));


        List<Integer> ints = Stream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).collect(Collectors.toList());

        ints.forEach((i) -> new Thread(() -> System.out.printf("Hi from Thread %d.%n", i)).start());

    }
}
