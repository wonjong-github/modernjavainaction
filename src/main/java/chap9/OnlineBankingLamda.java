package chap9;

import java.util.function.Consumer;

public class OnlineBankingLamda {
    public static void main(String[] args) {
        new OnlineBankingLamda().processCustomer(1228, (customer -> System.out.println(customer.getName())));
    }
    public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy.accept(c);
    }


    private static class Customer {

        public String getName() {
            return "Hello Customer";
        }
    }


    private static class Database {
        public static Customer getCustomerWithId(int id) {
            return new Customer();
        }
    }
}
