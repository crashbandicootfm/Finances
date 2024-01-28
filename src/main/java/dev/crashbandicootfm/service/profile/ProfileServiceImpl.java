package dev.crashbandicootfm.service.profile;

import dev.crashbandicootfm.profile.Profile;

import dev.crashbandicootfm.service.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import dev.crashbandicootfm.repository.ProfileRepository;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@Setter
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService, Service {

  @NotNull
  Map<UUID, Profile> loadedProfiles = new HashMap<>();

  ProfileRepository profileRepository;

  @Override
  public void enable() {
    profileRepository.getAll()
        .forEach(profile -> loadedProfiles.put(profile.getUuid(), profile));
  }

  @Override
  public void disable() {
    profileRepository.saveAll(loadedProfiles.values());
  }

  @Override
  public @Nullable Profile getProfile(@NotNull UUID uuid) {
    return loadedProfiles.get(uuid);
  }

  @Override
  public @Nullable Profile getProfile(@NotNull String name) {
    return loadedProfiles.values()
        .stream()
        .filter(profile -> profile.getName().equals(name))
        .findFirst().orElse(null);
  }

  @Override
  public void addProfile(@NotNull Profile profile) {
    loadedProfiles.put(profile.getUuid(), profile);
  }

  @Override
  public @NotNull List<Profile> getProfiles() {
    return new ArrayList<>(loadedProfiles.values());
  }

  @Override
  public @Nullable Profile getProfile(@NotNull String name, int id) {
    return loadedProfiles.values()
            .stream()
            .filter(profile -> profile.getName().equals(name) && profile.getId() == id)
            .findFirst().orElse(null);
  }
}
