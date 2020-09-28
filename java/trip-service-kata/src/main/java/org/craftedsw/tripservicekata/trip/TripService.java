package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

public class TripService {

  public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
    List<Trip> tripList = new ArrayList<Trip>();
    User loggedUser = getLoggedUser();
    if (loggedUser != null) {
      if (isFriend(user, loggedUser)) {
        tripList = getUserTrips(user);
      }
      return tripList;
    } else {
      throw new UserNotLoggedInException();
    }
  }

  private boolean isFriend(User user, User loggedUser) {
    boolean isFriend = false;
    for (User friend : user.getFriends()) {
      if (friend.equals(loggedUser)) {
        isFriend = true;
        break;
      }
    }
    return isFriend;
  }

  protected List<Trip> getUserTrips(User user) {
    return TripDAO.findTripsByUser(user);
  }

  protected User getLoggedUser() {
    return UserSession.getInstance().getLoggedUser();
  }

}
