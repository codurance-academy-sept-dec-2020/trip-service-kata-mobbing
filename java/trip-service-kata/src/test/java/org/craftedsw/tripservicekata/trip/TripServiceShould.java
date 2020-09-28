package org.craftedsw.tripservicekata.trip;

import static org.craftedsw.tripservicekata.trip.UserBuilder.*;
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
  void throw_an_exception_when_user_is_not_logged_in() {
    loggedInUser = GUEST_USER;
    assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(ANOTHER_USER));
  }

  @Test
  void return_no_trips_when_users_are_not_friends() {
    loggedInUser = REGISTERED_USER;
    User aUser = aUser()
        .withFriends(ANOTHER_USER)
        .build();

    assertThat(tripService.getTripsByUser(aUser), empty());
  }

  @Test
  void return_trips_when_users_are_friends() {
    loggedInUser = REGISTERED_USER;
    User aUser = aUser()
        .withFriends(ANOTHER_USER, loggedInUser)
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
    protected User getUser() {
      return loggedInUser;
    }
  }

}

