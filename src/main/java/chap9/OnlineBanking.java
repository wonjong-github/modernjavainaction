package chap9;

import java.util.function.Consumer;

abstract class OnlineBanking {
    public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy.accept(c);
    }

    abstract void makeCustomerHappy(Customer customer);

    static class Customer {
        String getName() {
            return "customerName";
        }
    }

    static class Database {
        static Customer getCustomerWithId(int id) {
            return new Customer();
        }
    }

}
