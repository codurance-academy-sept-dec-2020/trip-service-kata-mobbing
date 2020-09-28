package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TripServiceShould {

  @Test
  void tell_a_user_is_not_logged_in() {
    TripService tripService = new NotLoggedUserTripService();
    User user = new User();
    assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(user));
  }

  @Test
  void tell_a_logged_user_has_no_trips() {
    TripService tripService = new LoggedUserTripService();
    User user = new User();
    assertThat(tripService.getTripsByUser(user), empty());
  }

  class NotLoggedUserTripService extends TripService {

    public User getLoggedUser() {
      return null;
    }
  }

  class LoggedUserTripService extends TripService {

    @Override
    protected User getLoggedUser() {
      return new User();
    }
  }
}

