package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ChildResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ChildResponsePrinter {

  private static final String SEPARATOR = "-".repeat(52);
  private static final String ROW_FORMAT = "  %-15s : %s%n";

  private final ConsoleIO console;

  public void print(final ChildResponse child) {
    console.println(SEPARATOR);
    console.printf(ROW_FORMAT, "ID", child.id());
    console.printf(ROW_FORMAT, "Name", child.name());
    console.printf(ROW_FORMAT, "Birth date", child.birthDate());
    console.printf(ROW_FORMAT, "Admission", child.admissionDate());
    console.printf(ROW_FORMAT, "Exit date",
        child.exitDate() == null ? "Not registered" : child.exitDate());
    console.println(SEPARATOR);
  }

  public void printList(final List<ChildResponse> children) {
    if (children.isEmpty()) {
      console.println("  No children found.");
      return;
    }

    console.printf("%n  Total: %d child(ren)%n", children.size());
    children.forEach(this::print);
  }
}