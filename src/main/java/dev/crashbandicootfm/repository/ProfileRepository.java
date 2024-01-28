package dev.crashbandicootfm.repository;

import dev.crashbandicootfm.profile.Profile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public interface ProfileRepository {

    @NotNull @Unmodifiable List<Profile> getAll();
}
