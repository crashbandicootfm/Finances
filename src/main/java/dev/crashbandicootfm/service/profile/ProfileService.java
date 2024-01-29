package dev.crashbandicootfm.service.profile;

import dev.crashbandicootfm.service.Service;
import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ProfileService extends Service {

  @Nullable Profile getProfile(@NotNull UUID uuid);

  @Nullable Profile getProfile(@NotNull String name);

  void addProfile(@NotNull Profile profile);

  @NotNull List<Profile> getProfiles();

  @Nullable Profile getProfile(@NotNull String name, int id);
}
