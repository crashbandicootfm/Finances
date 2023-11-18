package dev.crashbandicootfm.service;

import dev.crashbandicootfm.profile.Profile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
public class ProfileService {

  @NonFinal
  Map<UUID, Profile> loadedProfiles = new HashMap<>();

  public @Nullable Profile getProfile(@NotNull UUID uuid) {
    return loadedProfiles.get(uuid);
  }

  public @Nullable Profile getProfile(@NotNull String name) {
    return loadedProfiles.values()
        .stream()
        .filter(profile -> profile.getName().equals(name))
        .findFirst().orElse(null);
  }

  public void addProfile(@NotNull Profile profile) {
    loadedProfiles.put(profile.getUuid(), profile);
  }

  public @NotNull List<Profile> getProfiles() {
    return new ArrayList<>(loadedProfiles.values());
  }


}
