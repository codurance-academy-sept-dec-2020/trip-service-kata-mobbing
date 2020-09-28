package org.craftedsw.tripservicekata.trip;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Test;

public class TripServiceShould {

  @Test
  void tell_a_user_is_not_logged_in() {
    TripService tripService = new StubTripService(null);
    User user = new User();
    assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(user));
  }

  @Test
  void tell_a_logged_user_has_no_trips() {
    User user = new User();
    TripService tripService = new StubTripService(user);
    assertThat(tripService.getTripsByUser(user), empty());
  }

  @Test
  void tell_a_logged_user_has_trips() {
    User user = new User();
    user.addTrip(new Trip());
    user.addFriend(user);
    TripService tripService = new StubTripService(user);
    assertThat(tripService.getTripsByUser(user), hasSize(1));
  }

  public class StubTripService extends TripService {

    private final User user;

    public StubTripService(User user) {
      this.user = user;
    }

    @Override
    protected List<Trip> getUserTrips(User user) {
      return user.trips();
    }

    @Override
    protected User getLoggedUser() {
      return user;
    }
  }

}

