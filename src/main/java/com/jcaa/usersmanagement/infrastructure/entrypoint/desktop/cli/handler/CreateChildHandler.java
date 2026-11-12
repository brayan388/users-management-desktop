package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ChildResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.ChildController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ChildResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateChildRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CreateChildHandler implements OperationHandler {

  private final ChildController childController;
  private final ConsoleIO console;
  private final ChildResponsePrinter printer;

  @Override
  public void handle() {
    console.println("\n  --- Create Child ---");

    final String id = console.readRequired("Child ID: ");
    final String name = console.readRequired("Child name: ");
    final String birthDate = console.readRequired("Birth date (YYYY-MM-DD): ");
    final String admissionDate = console.readRequired("Admission date (YYYY-MM-DD): ");

    final CreateChildRequest request =
        new CreateChildRequest(id, name, birthDate, admissionDate);

    final ChildResponse created = childController.createChild(request);

    console.println("\n  Child created successfully.");
    printer.print(created);
  }
}