package com.e2ec.boing4;

import org.whispersystems.libsignal.state.IdentityKeyStore;
import org.whispersystems.libsignal.state.PreKeyStore;
import org.whispersystems.libsignal.state.SessionStore;
import org.whispersystems.libsignal.state.SignedPreKeyStore;
import org.whispersystems.libsignal.IdentityKeyPair;
import org.whispersystems.libsignal.state.impl.InMemoryIdentityKeyStore;
import org.whispersystems.libsignal.state.impl.InMemoryPreKeyStore;
import org.whispersystems.libsignal.state.impl.InMemorySessionStore;
import org.whispersystems.libsignal.state.impl.InMemorySignedPreKeyStore;
import org.whispersystems.libsignal.util.KeyHelper;

public class SignalStores {
    public static SessionStore getSessionStore() {
        return new InMemorySessionStore();
    }

    public static PreKeyStore getPreKeyStore() {
        return new InMemoryPreKeyStore();
    }

    public static SignedPreKeyStore getSignedPreKeyStore() {
        return new InMemorySignedPreKeyStore();
    }

    public static IdentityKeyStore getIdentityKeyStore(IdentityKeyPair identityKeyPair) {
        return new InMemoryIdentityKeyStore(identityKeyPair, 1);
    }

    public static IdentityKeyPair generateIdentityKeyPair() {
        return KeyHelper.generateIdentityKeyPair(); // Ensure you import KeyHelper and handle initialization
    }
}
