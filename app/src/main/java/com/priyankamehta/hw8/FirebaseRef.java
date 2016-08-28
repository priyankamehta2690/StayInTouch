package com.priyankamehta.hw8;

import com.firebase.client.Firebase;

/**
 * Created by PriyankaMehta on 4/16/16.
 */
public class FirebaseRef {
    private static Firebase ref = new Firebase("https://boiling-torch-2456.firebaseio.com/");

    public static Firebase getRef() {
        return ref;
    }
}
