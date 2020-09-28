package org.craftedsw.tripservicekata.trip;

import static org.craftedsw.tripservicekata.trip.UserBuilder.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TripServiceShould {

  private final static User REGISTERED_USER = new User();
  private final static User GUEST_USER = null;
  private final static User ANOTHER_USER = new User();
  private final static Trip TRIP_TO_ROME = new Trip();
  @Mock
  private UserSession userSession;
  private TripService tripService;

  @BeforeEach
  void setUp() {
    tripService = new StubTripService(userSession);
  }

  @Test
  void throw_an_exception_when_user_is_not_logged_in() {
    when(userSession.getLoggedUser()).thenReturn(GUEST_USER);
    assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(ANOTHER_USER));
  }

  @Test
  void return_no_trips_when_users_are_not_friends() {
    when(userSession.getLoggedUser()).thenReturn(REGISTERED_USER);
    User aUser = aUser()
        .withFriends(ANOTHER_USER)
        .build();

    assertThat(tripService.getTripsByUser(aUser), empty());
  }

  @Test
  void return_trips_when_users_are_friends() {
    when(userSession.getLoggedUser()).thenReturn(REGISTERED_USER);
    User aUser = aUser()
        .withFriends(ANOTHER_USER, REGISTERED_USER)
        .withTrips(TRIP_TO_ROME)
        .build();

    assertThat(tripService.getTripsByUser(aUser), hasSize(1));
  }

  public class StubTripService extends TripService {

    public StubTripService(UserSession userSession) {
      super(userSession);
    }

    @Override
    protected List<Trip> getUserTrips(User user) {
      return user.trips();
    }
  }

}

