package repository;

import customer.CustomerAggregate;
import org.springframework.stereotype.Component;
import spi.repository.write.CustomerRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Component
public class CustomerRepositoryImpl implements CustomerRepository {
    private List<CustomerEntity> customerList;

    @Override
    public Optional<CustomerAggregate> findById(String customerId) {
        return customerList.stream().filter(customerEntity -> customerEntity.getId().equals(customerId))
                .map(customerEntity -> new CustomerAggregate(customerEntity.id, customerEntity.bonus))
                .findFirst();
    }

    @Override
    public void update(CustomerAggregate customerAggregate) {
        customerList.stream().filter(customerEntity -> customerEntity.getId().equals(customerAggregate.getCustomerId()))
                .findFirst()
                .ifPresent(customerEntity -> customerEntity.setBonus(customerAggregate.getBonus()));
    }

    public void setCustomerList(List<CustomerEntity> customerList) {
        this.customerList = customerList;
    }

    public static class CustomerEntity {

        public CustomerEntity(String id, BigInteger bonus) {
            this.id = id;
            this.bonus = bonus;
        }

        private final String id;

        public String getId() {

            return id;
        }

        public BigInteger getBonus() {
            return bonus;
        }

        public void setBonus(BigInteger bonus) {
            this.bonus = bonus;
        }

        private BigInteger bonus;

    }
}
