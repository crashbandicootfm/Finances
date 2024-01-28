package dev.crashbandicootfm.authorization;

import dev.crashbandicootfm.profile.Profile;
import org.jetbrains.annotations.NotNull;

public interface AuthorizationService {

    void authorize(@NotNull Profile profile);
}