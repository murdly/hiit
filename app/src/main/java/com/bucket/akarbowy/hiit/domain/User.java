package com.bucket.akarbowy.hiit.domain;

import com.parse.ParseClassName;
import com.parse.ParseRelation;
import com.parse.ParseUser;

/**
 * Created by akarbowy on 10.12.2015.
 */
@ParseClassName("User")
public class User extends ParseUser {

    public static ParseRelation<Technology> getSubsRelation() {
        return getCurrentUser().getRelation("mysubs");
    }
}
