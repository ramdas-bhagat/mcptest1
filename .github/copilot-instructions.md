## Repo Overview

- **Type:** Spring Boot Java service (single-module Gradle project).
- **Entry point:** `src/main/java/com/skyeagle/mcptest1/Application.java` (Spring Boot).
- **Core packages:**
  - `model` — domain POJOs using Lombok (e.g. `Book`).
  - `service` — in-memory business logic and state (`BookService`).
  - `tools` — MCP tool adapters annotated with `@McpTool` (see `BooksTool`).
  - `resource` — HTTP resources/controllers (currently placeholder `BookResource`).

## Big-picture architecture

- The app is a small Spring Boot service that keeps an in-memory list of `Book` objects. There is no persistence layer or Spring Data repository; state is initialized in `BookService.initializeBooks()` using `@PostConstruct`.
- Interaction surfaces:
  - Direct Java service calls via `BookService`.
  - Model Context Protocol (MCP) tools exposed in `tools` package with `@McpTool` / `@McpToolParam` annotations (the `springaicommunity.mcp` annotations are used).
  - HTTP resource package exists (`resource`) but is currently empty; prefer adding HTTP endpoints there when you need REST access.

## Key developer workflows

- Build (Windows PowerShell):

  ```powershell
  .\gradlew.bat build
  .\gradlew.bat test
  ```

- Run locally (Spring Boot):

  ```powershell
  .\gradlew.bat bootRun
  ```

- Debugging: VS Code launch currently uses a Java debug configuration (JDWP). You can also run the app with Gradle and attach a debugger.

## Project-specific patterns and conventions

- In-memory service pattern: `BookService` stores a `List<Book>` populated at startup. Methods operate on this list (filter, add, remove). When extending, follow the same pattern unless you intentionally add persistence.
- Model classes use Lombok annotations (`@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`). Ensure annotation processing is enabled locally (IDE plugin/annotation processor).
- MCP tools:
  - Place MCP-exposed methods in `src/main/java/com/skyeagle/mcptest1/tools/*`.
  - Annotate tool classes/methods with `@McpTool` and parameters with `@McpToolParam` (see `BooksTool`).
  - Tools delegate to service layer rather than contain business logic.

## Integration & dependencies to be aware of

- Uses Lombok (compile-time annotation processing). If you see missing getters/setters in IDE, enable Lombok support.
- Uses `springaicommunity.mcp` annotations for tool exposure — this is how the project exposes programmatic tools to AI/MCP integrations. Search for `@McpTool` to find all tool entrypoints.
- No external DB or messaging broker is configured in this repo; adding such integrations will require wiring them into `BookService` or new service classes.

## Where to look when adding features

- Business logic: `src/main/java/com/skyeagle/mcptest1/service/BookService.java`
- Tool adapters (for AI/MCP): `src/main/java/com/skyeagle/mcptest1/tools/BooksTool.java`
- Domain model: `src/main/java/com/skyeagle/mcptest1/model/Book.java`
- HTTP endpoints (add here): `src/main/java/com/skyeagle/mcptest1/resource/`

## Examples (copy-paste friendly)

- Add a new MCP tool method (pattern):

  ```java
  @McpTool(name = "find_by_author", description = "Find books by author")
  public List<Book> findByAuthor(@McpToolParam(description = "Author name") String author) {
      return bookService.getAllBooks().stream()
          .filter(b -> b.getAuthor().equalsIgnoreCase(author))
          .collect(Collectors.toList());
  }
  ```

- Find all MCP tools in repo:

  ```powershell
  Select-String -Path . -Pattern "@McpTool" -SimpleMatch -List
  ```

## Testing notes

- Unit tests are under `src/test/java`. Run them with `.\gradlew.bat test`.
- Because the service uses in-memory state, tests can rely on `BookService` initialization behavior or create new instances for isolation.

## Troubleshooting & quick tips

- If Lombok-generated members appear missing in the IDE, install the Lombok plugin and enable annotation processing.
- If adding a new dependency, update `build.gradle` and use the Gradle wrapper (`.\gradlew.bat`) to avoid local Gradle mismatches.
- When exposing new functionality to AI/MCP, prefer adding a `@McpTool` in `tools/` that calls into `service/` rather than putting logic directly in the tool.

---

If anything in this file looks incorrect or you'd like more details (examples for adding endpoints, test scaffolding, or MCP usage patterns), tell me which area to expand.
