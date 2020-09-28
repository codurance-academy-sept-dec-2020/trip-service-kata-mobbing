package org.craftedsw.tripservicekata.trip;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TripServiceShould {

  private final static User REGISTERED_USER = new User();
  private final static User GUEST_USER = null;
  private final static User ANOTHER_USER = new User();
  private final static Trip TRIP_TO_ROME = new Trip();
  private TripService tripService;
  private User loggedInUser;

  @BeforeEach
  void setUp() {
    tripService = new StubTripService();
    loggedInUser = GUEST_USER;
  }

  @Test
  void tell_a_user_is_not_logged_in() {
    loggedInUser = GUEST_USER;
    assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(ANOTHER_USER));
  }

  @Test
  void tell_a_logged_user_has_no_trips() {
    loggedInUser = REGISTERED_USER;
    User aUser = UserBuilder.aUser()
        .withFriends(loggedInUser)
        .build();

    assertThat(tripService.getTripsByUser(aUser), empty());
  }

  @Test
  void tell_a_logged_user_has_trips() {
    loggedInUser = REGISTERED_USER;
    User aUser = UserBuilder.aUser()
        .withFriends(loggedInUser)
        .withTrips(TRIP_TO_ROME)
        .build();

    assertThat(tripService.getTripsByUser(aUser), hasSize(1));
  }

  public class StubTripService extends TripService {

    @Override
    protected List<Trip> getUserTrips(User user) {
      return user.trips();
    }

    @Override
    protected User getLoggedUser() {
      return loggedInUser;
    }
  }

  private static class UserBuilder {

    private User[] users;
    private Trip[] trips;

    public UserBuilder() {
      this.users = new User[]{};
      this.trips = new Trip[]{};
    }

    public static UserBuilder aUser() {
      return new UserBuilder();
    }

    public UserBuilder withFriends(User... users) {
      this.users = users;
      return this;
    }

    public UserBuilder withTrips(Trip... trips) {
      this.trips = trips;
      return this;
    }

    public User build() {
      User aUser = new User();
      for (User user : users) {
        aUser.addFriend(user);
      }
      for (Trip trip : trips) {
        aUser.addTrip(trip);
      }
      return aUser;
    }
  }
}

