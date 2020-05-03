package dto;

import java.util.List;

public class CustomerDTO {

    public CustomerDTO(String customerId, String customerName, String subscriptionType, List<RentalDTO> rentalDTOList, int bonus) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.subscriptionType = subscriptionType;
        this.rentalDTOList = rentalDTOList;
        this.bonus = bonus;
    }

    public CustomerDTO() {
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public List<RentalDTO> getRentalDTOList() {
        return rentalDTOList;
    }

    public int getBonus() {
        return bonus;
    }

    private String customerId;
    private String customerName;
    private String subscriptionType;

    private List<RentalDTO> rentalDTOList;

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    private int bonus;
}
