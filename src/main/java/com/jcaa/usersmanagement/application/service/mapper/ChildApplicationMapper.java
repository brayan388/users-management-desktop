
package com.jcaa.usersmanagement.application.service.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateChildCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteChildCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateChildCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetChildByIdQuery;
import com.jcaa.usersmanagement.domain.model.ChildModel;
import com.jcaa.usersmanagement.domain.valueobject.ChildId;
import com.jcaa.usersmanagement.domain.valueobject.ChildName;
import java.time.LocalDate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ChildApplicationMapper {

  public ChildModel fromCreateCommandToModel(final CreateChildCommand command) {
    return ChildModel.create(
        new ChildId(command.id()),
        new ChildName(command.name()),
        LocalDate.parse(command.birthDate()),
        LocalDate.parse(command.admissionDate()));
  }

  public ChildModel fromUpdateCommandToModel(final UpdateChildCommand command) {
    final ChildName name = new ChildName(command.name());
    final LocalDate birthDate = LocalDate.parse(command.birthDate());
    final LocalDate admissionDate = LocalDate.parse(command.admissionDate());

    final ChildModel child =
        ChildModel.create(
            new ChildId(command.id()), name, birthDate, admissionDate);

    final LocalDate exitDate =
        command.exitDate() == null || command.exitDate().isBlank()
            ? null
            : LocalDate.parse(command.exitDate());

    return child.update(name, birthDate, admissionDate, exitDate);
  }

  public ChildId fromGetChildByIdQueryToChildId(final GetChildByIdQuery query) {
    return new ChildId(query.id());
  }

  public ChildId fromDeleteCommandToChildId(final DeleteChildCommand command) {
    return new ChildId(command.id());
  }
}
