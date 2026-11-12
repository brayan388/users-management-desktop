package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.ChildController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class DeleteChildHandler implements OperationHandler {

  private final ChildController childController;
  private final ConsoleIO console;

  @Override
  public void handle() {
    console.println("\n  --- Delete Child ---");

    final String id = console.readRequired("Child ID: ");

    childController.deleteChild(id);

    console.println("\n  Child deleted successfully.");
  }
}