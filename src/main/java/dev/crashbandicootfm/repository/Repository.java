package dev.crashbandicootfm.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public interface Repository<T> {

  @NotNull @Unmodifiable List<T> getAll();

  void save(@NotNull T entity);

  default void saveAll(@NotNull Collection<T> entities) {
    entities.forEach(this::save);
  }
   default Set<Integer> getUserIds() {
      return null;
  }

}
