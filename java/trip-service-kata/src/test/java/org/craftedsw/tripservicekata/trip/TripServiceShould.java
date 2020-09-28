package org.craftedsw.tripservicekata.trip;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Test;

public class TripServiceShould {

  @Test
  void tell_a_user_is_not_logged_in() {
    TripService tripService = new StubTripService(null, null);
    assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(new User()));
  }

  @Test
  void tell_a_logged_user_has_no_trips() {
    User loggedUser = new User();
    User friendsUser = new User();
    User[] friends = new User[]{loggedUser};
    TripService tripService = new StubTripService(loggedUser, friendsUser, friends, new Trip[]{});
    assertThat(tripService.getTripsByUser(friendsUser), empty());
  }

  @Test
  void tell_a_logged_user_has_trips() {
    User loggedUser = new User();
    User friendsUser = new User();
    User[] friends = new User[]{loggedUser};
    Trip[] trips = new Trip[]{new Trip()};

    TripService tripService = new StubTripService(loggedUser, friendsUser, friends, trips);
    assertThat(tripService.getTripsByUser(friendsUser), hasSize(1));
  }

  public class StubTripService extends TripService {

    private final User loggedUser;
    private final User friendsUser;

    public StubTripService(User loggedUser, User friendsUser, User[] friends, Trip[] trips) {
      this.loggedUser = loggedUser;
      this.friendsUser = friendsUser;
      Arrays.stream(friends).forEach(this.friendsUser::addFriend);
      Arrays.stream(trips).forEach(this.friendsUser::addTrip);
    }

    public StubTripService(User loggedUser, User otherUser) {
      this.loggedUser = loggedUser;
      this.friendsUser = otherUser;
    }

    @Override
    protected List<Trip> getUserTrips(User user) {
      return user.trips();
    }

    @Override
    protected User getLoggedUser() {
      return loggedUser;
    }
  }

}

