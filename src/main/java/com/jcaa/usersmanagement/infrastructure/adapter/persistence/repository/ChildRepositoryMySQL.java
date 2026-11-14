
package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.application.port.out.DeleteChildPort;
import com.jcaa.usersmanagement.application.port.out.GetAllChildrenPort;
import com.jcaa.usersmanagement.application.port.out.GetChildByIdPort;
import com.jcaa.usersmanagement.application.port.out.SaveChildPort;
import com.jcaa.usersmanagement.application.port.out.UpdateChildPort;
import com.jcaa.usersmanagement.domain.exception.ChildNotFoundException;
import com.jcaa.usersmanagement.domain.model.ChildModel;
import com.jcaa.usersmanagement.domain.valueobject.ChildId;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.ChildPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.ChildEntity;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper.ChildPersistenceMapper;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ChildRepositoryMySQL
    implements SaveChildPort,
        UpdateChildPort,
        GetChildByIdPort,
        GetAllChildrenPort,
        DeleteChildPort {

  private static final String SQL_INSERT =
      """
      INSERT INTO children (id, name, birth_date, admission_date, exit_date)
      VALUES (?, ?, ?, ?, ?)
      """;

  private static final String SQL_UPDATE =
      """
      UPDATE children
      SET name = ?, birth_date = ?, admission_date = ?, exit_date = ?
      WHERE id = ?
      """;

  private static final String SQL_FIND_BY_ID =
      """
      SELECT id, name, birth_date, admission_date, exit_date
      FROM children
      WHERE id = ?
      """;

  private static final String SQL_FIND_ALL =
      """
      SELECT id, name, birth_date, admission_date, exit_date
      FROM children
      ORDER BY name
      """;

  private static final String SQL_DELETE =
      "DELETE FROM children WHERE id = ?";

  private final Connection connection;

  @Override
  public ChildModel save(final ChildModel child) {
    final ChildPersistenceDto dto = ChildPersistenceMapper.fromModelToDto(child);

    try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
      statement.setString(1, dto.id());
      statement.setString(2, dto.name());
      statement.setDate(3, Date.valueOf(dto.birthDate()));
      statement.setDate(4, Date.valueOf(dto.admissionDate()));
      setExitDate(statement, 5, dto.exitDate());

      statement.executeUpdate();
      return findByIdOrFail(new ChildId(dto.id()));

    } catch (final SQLException exception) {
      throw PersistenceException.becauseSaveChildFailed(dto.id(), exception);
    }
  }

  @Override
  public ChildModel update(final ChildModel child) {
    final ChildPersistenceDto dto = ChildPersistenceMapper.fromModelToDto(child);

    try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
      statement.setString(1, dto.name());
      statement.setDate(2, Date.valueOf(dto.birthDate()));
      statement.setDate(3, Date.valueOf(dto.admissionDate()));
      setExitDate(statement, 4, dto.exitDate());
      statement.setString(5, dto.id());

      statement.executeUpdate();
      return findByIdOrFail(new ChildId(dto.id()));

    } catch (final SQLException exception) {
      throw PersistenceException.becauseUpdateChildFailed(dto.id(), exception);
    }
  }

  @Override
  public Optional<ChildModel> getById(final ChildId childId) {
    try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
      statement.setString(1, childId.value());

      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          final ChildEntity entity =
              ChildPersistenceMapper.fromResultSetToEntity(resultSet);

          return Optional.of(ChildPersistenceMapper.fromEntityToModel(entity));
        }

        return Optional.empty();
      }

    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindChildByIdFailed(
          childId.value(), exception);
    }
  }

  @Override
  public List<ChildModel> getAll() {
    try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL);
        ResultSet resultSet = statement.executeQuery()) {

      return ChildPersistenceMapper.fromResultSetToModelList(resultSet);

    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindAllChildrenFailed(exception);
    }
  }

  @Override
  public void delete(final ChildId childId) {
    try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
      statement.setString(1, childId.value());
      statement.executeUpdate();

    } catch (final SQLException exception) {
      throw PersistenceException.becauseDeleteChildFailed(
          childId.value(), exception);
    }
  }

  private ChildModel findByIdOrFail(final ChildId childId) {
    return getById(childId)
        .orElseThrow(
            () -> ChildNotFoundException.becauseIdWasNotFound(childId.value()));
  }

  private static void setExitDate(
      final PreparedStatement statement,
      final int parameterIndex,
      final java.time.LocalDate exitDate)
      throws SQLException {

    if (exitDate != null) {
      statement.setDate(parameterIndex, Date.valueOf(exitDate));
    } else {
      statement.setNull(parameterIndex, Types.DATE);
    }
  }
}
