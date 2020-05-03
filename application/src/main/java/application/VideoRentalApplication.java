package application;

import application.exceptions.*;
import command.CreateRentalCommand;
import command.ReturnRentalCommand;
import inventory.exceptions.InventoryEmptyOperation;
import rental.exceptions.InvalidReturnDate;

public class VideoRentalApplication {

    private final RentalCommandHandler rentalCommandHandler;
    private final InventoryCommandHandler inventoryCommandHandler;
    private final CustomerCommandHandler customerCommandHandler;

    public VideoRentalApplication(RentalCommandHandler rentalCommandHandler, InventoryCommandHandler inventoryCommandHandler, CustomerCommandHandler customerCommandHandler) {
        this.rentalCommandHandler = rentalCommandHandler;
        this.inventoryCommandHandler = inventoryCommandHandler;
        this.customerCommandHandler = customerCommandHandler;
    }

    public void handle(CreateRentalCommand createRentalCommand) {

        try {
            inventoryCommandHandler.handle(createRentalCommand);
        } catch (InventoryEmptyOperation e) {
            throw new InternalApplicationException(e);
        } catch (NoInventoryFound e) {
            throw new UnvalidParameterException(e);
        }

        try {
            rentalCommandHandler.handle(createRentalCommand);
        } catch (IllegalArgumentException e) {
            //TODO compensate inventory
            throw new UnvalidParameterException(e);
        }

        try {
            customerCommandHandler.handle(createRentalCommand);
        } catch (NoCustomerFound e) {
            //TODO compensate inventory
            //TODO compensate rental
            throw new UnvalidParameterException(e);
        }
    }

    public void handle(ReturnRentalCommand returnRentalCommand) {

        try {
            inventoryCommandHandler.handle(returnRentalCommand);
        } catch (InventoryEmptyOperation e) {
            throw new InternalApplicationException(e);
        } catch (NoInventoryFound e) {
            throw new UnvalidParameterException(e);
        }

        try {
            rentalCommandHandler.handle(returnRentalCommand);
        } catch (IllegalArgumentException | NoRentalFound | InvalidReturnDate e) {
            //TODO compensate inventory
            throw new UnvalidParameterException(e);
        }


    }
}
