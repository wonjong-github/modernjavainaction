package chap9;

import chap2.Apple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Slf4j
public class Main {
    public static void main(String[] args) {

        // 9.1.2 익명 클래스를 람다 표현식으로 리팩터링하기
        doSomething(new Task() {
            @Override
            public void execute() {
                System.out.println("Main.execute");
            }
        });

        doSomething((Task) () -> System.out.println("Main.main"));

        // 9.1.3 람다 표현식을 메서드 참조로 리팩터링하기
        List<Dish> menu = Dish.menu;
        Map<Dish.CaloricLevel, List<Dish>> collect = menu.stream().collect(Collectors.groupingBy(Dish::getCaloricLevel));
        System.out.println("collect = " + collect);

        List<Apple> inventory = new ArrayList<>();
        inventory.sort(Comparator.comparing(Apple::getWeight));

        // 9.1.4 명령형 데이터 처리를 스트림으로 리팩터링하기
        List<String> collect1 = menu.parallelStream().filter(d -> d.getCalories() > 300)
                .map(Dish::getName)
                .collect(Collectors.toList());
        System.out.println("collect1 = " + collect1);
        // 9.1.5 코드 유연성 개선
        // 함수형 인터페이스 적용
        // 조건부 연기 실행
        // 9.2.2 템플릿 메서드
        // 9.2.3 옵저버
        Subject.Feed feed = new Subject.Feed();
        feed.registerObserver(new Observer.NYTimes());
        feed.registerObserver(new Observer.Guadinan());
        feed.registerObserver(new Observer.Lemonde());
        feed.notifyObservers("The queen");

        feed.registerObserver((String tweet) -> {
            if (tweet != null && tweet.contains("money")) {
                System.out.println("This is money" + tweet);
            }
        });

        // 9.2.4 의무 체인
        ProcessingObject<String> headerTextProcessing = new HeaderTextProcessing();
        ProcessingObject<String> spellCheckerProcessing = new SpellCheckerProcessing();
        headerTextProcessing.setSuccessor(spellCheckerProcessing);
        String result = headerTextProcessing.handle("labda is labda");
        System.out.println("result = " + result);

        UnaryOperator<String> headerProcessing = (String text) -> "From wonjong : " + text;
        UnaryOperator<String> spellCheckProcessing = (String text) -> text.replaceAll("labda", "lambda");
        Function<String, String> pipeline = headerProcessing.andThen(spellCheckProcessing);
        String labdaIsLabda = pipeline.apply("labda is labda");
        System.out.println("labdaIsLabda = " + labdaIsLabda);
    }
    interface Task{
        public void execute();
    }

    public static void doSomething(Runnable r) {
        r.run();
    }

    public static void doSomething(Task task) {
        task.execute();
    }
}
