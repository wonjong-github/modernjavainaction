package chap18;

import org.springframework.stereotype.Controller;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    static List<List<Integer>> subsets(List<Integer> list) {
        if (list.isEmpty()) {
            List<List<Integer>> ans = new ArrayList<>();
            ans.add(Collections.EMPTY_LIST);
            return ans;
        }
        Integer first = list.get(0);
        List<Integer> rest = list.subList(1, list.size());

        List<List<Integer>> subans = subsets(rest);
        List<List<Integer>> subans2 = insertAll(first, subans);
        return concat(subans, subans2);
    }

    public static <T> List<List<T>> insertAll(T first, List<List<T>> lists) {
        List<List<T>> result = new ArrayList<>();
        for (List<T> l : lists) {
            List<T> copyList = new ArrayList<>();
            copyList.add(first);
            copyList.addAll(l);
            result.add(copyList);
        }
        return result;
    }

    static <T> List<List<T>> concat(List<List<T>> a, List<List<T>> b) {
        List<List<T>> r = new ArrayList<>(a);
        r.addAll(b);
        return r;
    }
    public static void main(String[] args) {
        List<List<Integer>> subsets = subsets(Arrays.asList(1, 4, 9));
        System.out.println("subsets = " + subsets);
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
//        var a = "asdf";

        ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.put("A", 1);
        concurrentMap.put("B", 2);
        concurrentMap.put("C", 3);

        int sum = concurrentMap.reduce(2, (key, value) -> value, Integer::sum);
        long mappingCount = concurrentMap.mappingCount();
        System.out.println("Sum: " + sum);
        System.out.println("mappingCount = " + mappingCount);
        int[] evenNum = new int[10];
        Arrays.setAll(evenNum, i -> i * 2);
        int[] ones = new int[10];
        Arrays.fill(ones, 1);
        Arrays.parallelPrefix(ones, (a, b) -> a + b);

//        Math.subtractExact()
    }
}
