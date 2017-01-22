package ru.glaizier;

import java.util.stream.Stream;

public class HelloWorld {

    public static void main(String[] args) {

        System.out.println(Stream.of("fjak", "f").collect(
                () -> new int[]{0},
                (i, s) -> i[0] += s.length(),
                (i1, i2) -> i1[0] += i2[0]
        )[0]);

        System.out.println(Stream.of("fjak", "f")
                .mapToInt(String::length)
                .sum());


        System.out.println("Hello world!");
    }

}
