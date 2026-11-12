package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ChildResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.ChildController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ChildResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateChildRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UpdateChildHandler implements OperationHandler {

  private final ChildController childController;
  private final ConsoleIO console;
  private final ChildResponsePrinter printer;

  @Override
  public void handle() {
    console.println("\n  --- Update Child ---");

    final String id = console.readRequired("Child ID: ");
    final String name = console.readRequired("New child name: ");
    final String birthDate = console.readRequired("Birth date (YYYY-MM-DD): ");
    final String admissionDate = console.readRequired("Admission date (YYYY-MM-DD): ");
    final String exitDate =
        console.readOptional("Exit date (YYYY-MM-DD, leave blank if not registered): ");

    final UpdateChildRequest request =
        new UpdateChildRequest(
            id,
            name,
            birthDate,
            admissionDate,
            exitDate.isBlank() ? null : exitDate);

    final ChildResponse updated = childController.updateChild(request);

    console.println("\n  Child updated successfully.");
    printer.print(updated);
  }
}