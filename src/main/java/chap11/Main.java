package chap11;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("Main.main");
        Main main = new Main();
        Insurance insurance = null;
        Optional<Insurance> optInsurance = Optional.ofNullable(insurance);
        Optional<String> name = optInsurance.map(Insurance::getName);

        Person person = new Person();
        Optional<Person> optPerson = Optional.of(person);
//        Optional<String> name1 = optPerson.map(Person::getCar).map(Car::getInsurance).map(Insurance::getName);
        Set<String> carInsuranceNames = main.getCarInsuranceNames(List.of(new Person(), new Person(), new Person()));

    }

    public String getCarInsuranceName(Optional<Person> person) {
        return person.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");
    }

    public Set<String> getCarInsuranceNames(List<Person> persons) {
        return persons.stream()
                .map(Person::getCar)
                .map(optCar -> optCar.flatMap(Car::getInsurance))
                .map(optIns -> optIns.map(Insurance::getName))
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());
    }

    public Insurance findCheapestInsurance(Person person, Car car) {
        return new Insurance();
    }

//    public Optional<Insurance> nullSafeFindCheapestInsurance(
//            Optional<Person> person, Optional<Car> car) {
//        if (person.isPresent() && car.isPresent()) {
//            return Optional.of(findCheapestInsurance(person.get(), car.get()));
//        }
//        return Optional.empty();
//    }

    public Optional<Insurance> nullSafeFindCheapestInsurance(
            Optional<Person> person, Optional<Car> car) {
//        return person.flatMap(p -> car.map(c -> findCheapestInsurance(p, c)));
        return person.flatMap(p -> car.map(c -> findCheapestInsurance(p, c)));
    }

    public String getCarInsuranceName(Optional<Person> person, int minAge) {
        return person.filter(p -> p.getAge() >= minAge).flatMap(Person::getCar).flatMap(Car::getInsurance).map(Insurance::getName).orElse("Unknown");
    }

    public int readDuration(Properties props, String name) {
        return Optional.ofNullable(props.getProperty(name))
                .flatMap(v -> stringToInt(v))
                .filter(n -> n > 0)
                .orElse(0);
    }

    private static Optional<Integer> stringToInt(String value) {
        return Optional.of(Integer.parseInt(value));
    }
}

