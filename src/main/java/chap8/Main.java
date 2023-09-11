package chap8;

import chap5.Trader;
import chap5.Transaction;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class Main {

    private static MessageDigest messageDigest ;


    public static void main(String[] args) throws NoSuchAlgorithmException {

//        List<String> friends = new ArrayList<>();
//        friends.add("R");
//        friends.add("O");
//        friends.add("T");

//        List<String> friends2 = Arrays.asList("R", "O", "T");

        List<String> friends = List.of("R", "O", "T", "T");
//        friends.add("C"); //에러 발생
//        Set<String> f = Set.of("R", "O", "T", "T");
//        Map<String, Integer> ageOfFriends = Map.of("R", 30, "O", 24, "T", 26);
//        System.out.println("ageOfFriends = " + ageOfFriends);

        Map<String, Integer> ageOfFriends = Map.ofEntries(Map.entry("R", 30),
                Map.entry("O", 25),
                Map.entry("T", 26));
        System.out.println("ageOfFriends = " + ageOfFriends);

        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        transactions.removeIf(t -> t.getYear() > 2022);

//        for (Transaction transaction : transactions) {
//            if (transaction.getYear() < 2012) {
//                transactions.remove(transaction);
//            }
//        }
        List<String> codes = Arrays.asList("R", "I");
        codes.replaceAll(c -> (c + c));
        codes.forEach(System.out::println);

        ageOfFriends.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(System.out::println);
        ageOfFriends.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(System.out::println);
        System.out.println(ageOfFriends.getOrDefault("C", 17));
        System.out.println(ageOfFriends.getOrDefault("O", 10932874));

        Map<String, byte[]> dataToHash = new HashMap<>();
        messageDigest = MessageDigest.getInstance("SHA-256");

        List<String> lines = Arrays.asList(
                " Nel   mezzo del cammin  di nostra  vita ",
                "mi  ritrovai in una  selva oscura",
                " che la  dritta via era   smarrita ",
                "이원종"
        );
        lines.forEach(l -> dataToHash.computeIfAbsent(l, Main::calculateDigest));
        System.out.println("dataToHash = " + dataToHash );

        // 교체 패턴
        Map<String, String> favouriteMovies = new HashMap<>();
        favouriteMovies.put("R", "Star");
        favouriteMovies.put("O", "james");
        favouriteMovies.replaceAll((friend, movie) -> movie.toUpperCase());
        System.out.println("favouriteMovies = " + favouriteMovies);

        //8.3.7 합침
        Map<String, String> family = Map.ofEntries(Map.entry("Teo", "Star wars"), Map.entry("Cristina", "James Bond"));
        Map<String, String> friendsMap = Map.ofEntries(Map.entry("Raphael", "Star wars"), Map.entry("Cristina", "Matrix"));
        Map<String, String> everyone = new HashMap<>(family);
        friendsMap.forEach((k, v) -> everyone.merge(k, v, (movie1, movie2) -> movie1 + "&" + movie2));
        System.out.println("everyone = " + everyone);

        Map<String, Long> moviesToCount = new HashMap<>();
        String moviename = "JamesBond";
        moviesToCount.merge(moviename, 1L, (movie, count) -> count + 1L);
        System.out.println("moviesToCount = " + moviesToCount);

        Map<String, Integer> movies = new HashMap<>();
        movies.put("JamesBond", 20);
        movies.put("Matrix", 10);
        movies.put("HarryPoter", 5);
        Iterator<Map.Entry<String, Integer>> iterator = movies.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            if (entry.getValue() < 10) {
                iterator.remove();
            }
        }
        movies.entrySet().removeIf(e -> e.getValue() < 10);

        /**
         * 8.4 개선된 ConcurrentHashMap
         */

        // 8.4.1 리듀스와 검색
        ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
        map.put("A", 1L);
        map.put("B", 2L);
        long parallelismThreshold = 1;
        Optional<Long> maxValue = Optional.ofNullable(map.reduceValues(parallelismThreshold, Long::max));
        System.out.println("maxValue = " + maxValue.get());
        System.out.println("map.mappingCount() = " + map.mappingCount());
        ConcurrentHashMap.KeySetView<String, Long> view = map.keySet();
        System.out.println("view = " + view);
        map.put("C", 3L);
        System.out.println("view = " + view);
        ConcurrentHashMap.KeySetView<Object, Boolean> objects = ConcurrentHashMap.newKeySet();
        objects.addAll(view);
        System.out.println("objects = " + objects);
    }

    private static byte[] calculateDigest(String key) {
        return messageDigest.digest(key.getBytes(StandardCharsets.UTF_8));
    }

}
