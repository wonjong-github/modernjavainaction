package chap3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiPredicate;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import chap2.Apple;
import chap2.Color;

public class Main {
    public static void main(String[] args) throws IOException {
        String k = processFile((BufferedReader br) -> br.readLine() + br.readLine());
        System.out.println(k);
        Predicate<String> tmp = "G"::equals;

        System.out.println(tmp.test("t"));

        Predicate<String> nonEmptyString = (String s) -> !s.isEmpty();

        List<String> sampList = new ArrayList<>();
        sampList.add("s");
        sampList.add("");
        sampList.stream().forEach(s-> System.out.println(s));
        List<String> nonEmpList = filter(sampList, nonEmptyString);
        nonEmpList.stream().forEach(s-> System.out.println(s));

        List<Integer> l = map(Arrays.asList("hello", "k", ""), (String s)->s.length());
        
        l.stream().forEach(s->System.out.println(s));

        // Function<BufferedReader, String> f = (BufferedReader br) -> {
        //     try {
        //         return br.readLine();
        //     } catch (Exception e) {
        //         // TODO: handle exception
        //     }
        //     return "hello";
        // };

        // () -> V
        // Callable<Integer> c = () -> 42;
        Callable<String> c = () -> "hello";
        Supplier<String> s = () -> "hello";

        Runnable r = () -> {System.out.println();};

        List<Apple> inventory = new ArrayList<>();
        inventory.sort((Apple a, Apple b) -> a.getWeight());
        
        inventory.sort(Comparator.comparing(Apple::getWeight));
        
        // TriFunction<Integer, Integer, Integer, Color> tf = Color::new;

        // Color c1 = tf.apply(25, 12, 40);

        // System.out.println(c1.getX());

        inventory.sort(new AppleComparator());

        inventory.sort(new Comparator<Apple>() {

            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
            
        });


        inventory.sort((Apple o1, Apple o2) -> o1.getWeight().compareTo(o2.getWeight()));

        inventory.sort(Comparator.comparing((apple -> apple.getWeight() )));

        inventory.sort(Comparator.comparing(Apple::getWeight));

        Comparator<Apple> comparator = Comparator.comparing(Apple::getWeight).reversed().thenComparing(Apple::getColor);

        Predicate<Apple> redApple = (Apple a) -> a.getColor().equals("RED");
        Predicate<Apple> nonRedApple = redApple.negate();
        Predicate<Apple> redHeavyApple = redApple.and(apple -> apple.getWeight() > 150);
        Predicate<Apple> redHeavyOrGreenApple = redApple.and(apple -> apple.getWeight() > 150).or(apple -> apple.getColor().equals(Color.GREEN));

        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Function<Integer, Integer> h = f.andThen(g);

        int result = h.apply(1);
        System.out.println(result);

        h = f.compose(g);
        result = h.apply(1);
        System.out.println(result);
        

        
        // try {
        //     String k = processFile((BufferedReader br) -> br.readLine());
        // }catch (IOException e){
        //     System.out.println("IOException");
        // }
        // List<Integer> result = map(
        //         Arrays.asList("asdf", "adsfee", "qww"),
        //         (String s) -> s.length()
        // );
        // System.out.println(processFile());

        List<String> str = Arrays.asList("a", "b", "a", "b");
        str.sort(String::compareToIgnoreCase);

        // ToIntFunction<String> stringToInt = (String s2) -> Integer.parseInt(s2);
        ToIntFunction<String> stringToInt = Integer::parseInt;

        // BiPredicate<List<String>, String> contains = (list, element) -> list.contains(element);
        BiPredicate<List<String>, String> contains = List::contains;

        // Predicate<String> startsWithNumber = (String string) -> this.startsWithNumber(string);

        
    }

    public double integrate(DoubleFunction<Double> f, double a, double b){
        return f.apply(a) + f.apply(b);
    }


    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
            List<T> result = new ArrayList<>();
            for (T t : list) {
                if (p.test(t)){
                    result.add(t);
                }
            }
            return result;
    }

    // public static String processFile() throws IOException{
    //     try (BufferedReader br = new BufferedReader(new FileReader("C:/__dev__/VSCodeProjects/modernjava/project/src/chap3/data.txt"))) {
    //         return br.readLine();
    //     } 
    // }
    
    public static String processFile(BufferedReaderProcessor p) throws IOException{
        try(BufferedReader br = new BufferedReader(new FileReader("C:/__dev__/VSCodeProjects/modernjava/project/src/chap3/data.txt"))){
            return p.process(br);
        }
    }



    // public static String processFile(BufferedReaderProcessor p) throws IOException{
    //     try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))){
    //         return p.process(br);
    //     }
    // }
    public static <T, R> List<R> map(List<T> list, Function<T, R> f){
        List<R> result = new ArrayList<>();
        for (T t : list) {
            result.add(f.apply(t));     
        }
        return result;
    }
}
