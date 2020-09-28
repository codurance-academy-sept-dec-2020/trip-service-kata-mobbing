package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TripServiceTest {
    @Test
    void user_is_not_logged_should_throws_an_exception() {
        TripService tripService = new FakeTripService();
        User user = new User();
        assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(user));
    }


    class FakeTripService extends TripService {
        public User getLoggedUser() {
            return null;
        }
    }
}

