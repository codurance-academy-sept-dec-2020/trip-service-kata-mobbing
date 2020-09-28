package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.user.User;

class UserBuilder {

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
