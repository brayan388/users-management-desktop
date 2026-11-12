package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateChildCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteChildCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateChildCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetChildByIdQuery;
import com.jcaa.usersmanagement.domain.model.ChildModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ChildResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateChildRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateChildRequest;

import java.util.List;

public final class ChildDesktopMapper {

  private ChildDesktopMapper() {}

  public static CreateChildCommand toCreateCommand(final CreateChildRequest request) {
    return new CreateChildCommand(
        request.id(),
        request.name(),
        request.birthDate(),
        request.admissionDate());
  }

  public static UpdateChildCommand toUpdateCommand(final UpdateChildRequest request) {
    return new UpdateChildCommand(
        request.id(),
        request.name(),
        request.birthDate(),
        request.admissionDate(),
        request.exitDate());
  }

  public static DeleteChildCommand toDeleteCommand(final String id) {
    return new DeleteChildCommand(id);
  }

  public static GetChildByIdQuery toGetByIdQuery(final String id) {
    return new GetChildByIdQuery(id);
  }

  public static ChildResponse toResponse(final ChildModel child) {
    return new ChildResponse(
        child.getId().value(),
        child.getName().value(),
        child.getBirthDate().toString(),
        child.getAdmissionDate().toString(),
        child.getExitDate() == null ? null : child.getExitDate().toString());
  }

  public static List<ChildResponse> toResponseList(final List<ChildModel> children) {
    return children.stream().map(ChildDesktopMapper::toResponse).toList();
  }
}