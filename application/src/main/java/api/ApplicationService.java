package api;

import application.VideoRentalApplication;
import command.CreateRentalCommand;
import command.ReturnRentalCommand;

public class ApplicationService {

    final private VideoRentalApplication videoRentalApplication;

    public ApplicationService(VideoRentalApplication videoRentalApplication) {
        this.videoRentalApplication = videoRentalApplication;
    }

    public void createRental(CreateRentalCommand createRentalCommand) {
        //do...
        videoRentalApplication.handle(createRentalCommand);
    }

    public void returnRental(ReturnRentalCommand returnRentalCommand) {
        videoRentalApplication.handle(returnRentalCommand);
    }
}
