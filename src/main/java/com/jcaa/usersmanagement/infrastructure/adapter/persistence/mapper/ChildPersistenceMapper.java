
package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import com.jcaa.usersmanagement.domain.model.ChildModel;
import com.jcaa.usersmanagement.domain.valueobject.ChildId;
import com.jcaa.usersmanagement.domain.valueobject.ChildName;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.ChildPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.ChildEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ChildPersistenceMapper {

  public ChildPersistenceDto fromModelToDto(final ChildModel child) {
    return new ChildPersistenceDto(
        child.getId().value(),
        child.getName().value(),
        child.getBirthDate(),
        child.getAdmissionDate(),
        child.getExitDate());
  }

  public ChildEntity fromResultSetToEntity(final ResultSet resultSet)
      throws SQLException {
    return new ChildEntity(
        resultSet.getString("id"),
        resultSet.getString("name"),
        resultSet.getDate("birth_date").toLocalDate(),
        resultSet.getDate("admission_date").toLocalDate(),
        resultSet.getDate("exit_date") != null
            ? resultSet.getDate("exit_date").toLocalDate()
            : null);
  }

  public ChildModel fromEntityToModel(final ChildEntity entity) {
    return new ChildModel(
        new ChildId(entity.id()),
        new ChildName(entity.name()),
        entity.birthDate(),
        entity.admissionDate(),
        entity.exitDate());
  }

  public ChildModel fromResultSetToModel(final ResultSet resultSet)
      throws SQLException {
    return fromEntityToModel(fromResultSetToEntity(resultSet));
  }

  public List<ChildModel> fromResultSetToModelList(final ResultSet resultSet)
      throws SQLException {
    final List<ChildModel> children = new ArrayList<>();

    while (resultSet.next()) {
      children.add(fromResultSetToModel(resultSet));
    }

    return children;
  }
}
