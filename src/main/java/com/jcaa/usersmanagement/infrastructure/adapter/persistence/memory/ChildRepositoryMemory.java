
package com.jcaa.usersmanagement.infrastructure.adapter.persistence.memory;

import com.jcaa.usersmanagement.application.port.out.DeleteChildPort;
import com.jcaa.usersmanagement.application.port.out.GetAllChildrenPort;
import com.jcaa.usersmanagement.application.port.out.GetChildByIdPort;
import com.jcaa.usersmanagement.application.port.out.SaveChildPort;
import com.jcaa.usersmanagement.application.port.out.UpdateChildPort;
import com.jcaa.usersmanagement.domain.model.ChildModel;
import com.jcaa.usersmanagement.domain.valueobject.ChildId;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class ChildRepositoryMemory
    implements SaveChildPort,
        GetChildByIdPort,
        GetAllChildrenPort,
        UpdateChildPort,
        DeleteChildPort {

  private final ConcurrentMap<ChildId, ChildModel> children =
      new ConcurrentHashMap<>();

  @Override
  public ChildModel save(final ChildModel child) {
    final ChildModel existing = children.putIfAbsent(child.getId(), child);

    if (existing != null) {
      throw new IllegalArgumentException(
          "A child with id '" + child.getId().value() + "' already exists.");
    }

    return child;
  }

  @Override
  public Optional<ChildModel> getById(final ChildId childId) {
    return Optional.ofNullable(children.get(childId));
  }

  @Override
  public List<ChildModel> getAll() {
    return List.copyOf(children.values());
  }

  @Override
  public ChildModel update(final ChildModel child) {
    final ChildModel existing = children.replace(child.getId(), child);

    if (existing == null) {
      throw new IllegalArgumentException(
          "A child with id '" + child.getId().value() + "' was not found.");
    }

    return child;
  }

  @Override
  public void delete(final ChildId childId) {
    final ChildModel removed = children.remove(childId);

    if (removed == null) {
      throw new IllegalArgumentException(
          "A child with id '" + childId.value() + "' was not found.");
    }
  }
}
