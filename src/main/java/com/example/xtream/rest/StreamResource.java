package com.example.xtream.rest;

import com.example.xtream.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream is lazy until terminal op
 * They do not change the original data
 * They produce a new result in return
 * Types of operation:
 * 1. Source Operations (Creation)
 * Stream.of(1, 2, 3);
 * list.stream();
 * Arrays.stream(arr);
 * 2. Intermediate Operations
 * filter()
 * map()
 * flatMap()
 * sorted()
 * distinct()
 * limit()
 * skip()
 * peek()
 * etc...
 * 3. Terminal Operations
 * collect()
 * forEach()
 * count()
 * findFirst()
 * reduce()
 * anyMatch()
 */
@RestController
@RequestMapping("api/stream")
//@Secured("AD")
public class StreamResource {

    /**
     * Init Source
     */
    List<Integer> list = Arrays.asList(1, 2, 2, 3, 4, 5);

    int[] arr = {5, 3, 1, 2, 4};

    /**
     * Foreach
     */
    @GetMapping("/foreach")
    public ResponseEntity<ResponseDTO> foreach() {

        List<Integer> result = new ArrayList<>();

        list.stream().forEach(result::add); // collect manually

        return ResponseEntity.ok(
                ResponseDTO.builder().response(result).build()
        );
    }

    /**
     * Reduction
     */
    @GetMapping("/reduction")
    public ResponseEntity<ResponseDTO> reduction() {

        Map<String, Object> result = new HashMap<>();

        /*
         * 1. Sum
         */
        int sum = list.stream().reduce(0, Integer::sum);
        result.put("sum", sum);

        /*
         * 2. Product
         */
        int product = list.stream().reduce(1, (a, b) -> a * b);
        result.put("product", product);

        /*
         * 3. Max
         */
        int max = list.stream().reduce(Integer.MIN_VALUE, Integer::max);
        result.put("max", max);

        /*
         * 4. Min
         */
        int min = list.stream().reduce(Integer.MAX_VALUE, Integer::min);
        result.put("min", min);

        /*
         * 5. Sum of even numbers (combine filter + reduce)
         */
        int sumEven = list.stream()
                .filter(x -> x % 2 == 0)
                .reduce(0, Integer::sum);
        result.put("sumEven", sumEven);

        /*
         * 6. Count using reduce (for learning purpose)
         */
        int count = list.stream()
                .reduce(0, (acc, x) -> acc + 1);
        result.put("countUsingReduce", count);

        /*
         * 7. String concatenation
         */
        String concat = list.stream()
                .map(String::valueOf)
                .reduce("", (a, b) -> a + "-" + b);
        result.put("concat", concat);

        /*
         * 8. Without identity (Optional result)
         */
        Optional<Integer> optionalSum = list.stream()
                .reduce((a, b) -> a + b);
        result.put("optionalSum", optionalSum.orElse(null));

        /*
         * 9. Parallel reduce (with combiner)
         */
        int parallelSum = list.parallelStream()
                .reduce(0,
                        Integer::sum,   // accumulator
                        Integer::sum);  // combiner
        result.put("parallelSum", parallelSum);

        return ResponseEntity.ok(
                ResponseDTO.builder().response(result).build()
        );
    }

    /**
     * collect
     */
    @GetMapping("/collect")
    public ResponseEntity<ResponseDTO> collect() {

        Map<String, Object> result = new HashMap<>();

        // toList
        result.put("toList",
                list.stream()
                        .map(x -> x * 2)
                        .collect(Collectors.toList())
        );

        // toSet
        result.put("toSet",
                list.stream().collect(Collectors.toSet())
        );

        // toMap
        result.put("toMap",
                list.stream()
                        .distinct()
                        .collect(Collectors.toMap(
                                x -> x + x,
                                x -> x * x
                        ))
        );

        return ResponseEntity.ok(
                ResponseDTO.builder().response(result).build()
        );
    }

    /**
     * Count
     */
    @GetMapping("/count")
    public ResponseEntity<ResponseDTO> count() {

        long count = list.stream().count();

        return ResponseEntity.ok(
                ResponseDTO.builder().response(count).build()
        );
    }

    /**
     * Matching
     */
    @GetMapping("/matching")
    public ResponseEntity<ResponseDTO> matching() {

        Map<String, Object> result = new HashMap<>();

        result.put("anyMatch (>3)", list.stream().anyMatch(x -> x > 3));
        result.put("allMatch (>0)", list.stream().allMatch(x -> x > 0));
        result.put("noneMatch (<0)", list.stream().noneMatch(x -> x < 0));

        return ResponseEntity.ok(
                ResponseDTO.builder().response(result).build()
        );
    }

    /**
     * Finding (Optional results)
     */
    @GetMapping("/finding")
    public ResponseEntity<ResponseDTO> finding() {

        Map<String, Object> result = new HashMap<>();

        result.put("findFirst",
                list.stream().findFirst().orElse(null));

        result.put("findAny",
                list.stream().findAny().orElse(null));

        return ResponseEntity.ok(
                ResponseDTO.builder().response(result).build()
        );
    }

    /**
     * Min / Max
     */
    @GetMapping("/min-max")
    public ResponseEntity<ResponseDTO> minMax() {

        Map<String, Object> result = new HashMap<>();

        result.put("min",
                list.stream().min(Integer::compareTo).orElse(null));

        result.put("max",
                list.stream().max(Integer::compareTo).orElse(null));

        return ResponseEntity.ok(
                ResponseDTO.builder().response(result).build()
        );
    }

    /**
     * Conversion to Array
     */
    @GetMapping("/to-array")
    public ResponseEntity<ResponseDTO> toArray() {

        Map<String, Object> result = new HashMap<>();

        result.put("Object[]",
                Arrays.toString(list.stream().toArray()));

        result.put("Integer[]",
                Arrays.toString(list.stream().toArray(Integer[]::new)));

        return ResponseEntity.ok(
                ResponseDTO.builder().response(result).build()
        );
    }
}