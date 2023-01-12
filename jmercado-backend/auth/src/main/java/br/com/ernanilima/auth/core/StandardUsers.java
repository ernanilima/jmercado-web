package br.com.ernanilima.auth.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StandardUsers {

    public static final UUID UNAUTHENTICATED_UUID = UUID.fromString("f134a511-d4bb-44a5-8bf9-bc9e73f5beda");

}
