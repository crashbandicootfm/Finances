package dev.crashbandicootfm.service.authorization;

import dev.crashbandicootfm.service.profile.Profile;
import org.jetbrains.annotations.NotNull;

public interface AuthorizationService {

    void authorize(@NotNull Profile profile);
}