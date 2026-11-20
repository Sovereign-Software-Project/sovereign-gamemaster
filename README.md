# Sovereign GameMaster

A Kotlin and Compose desktop application for managing, monitoring, and inspecting containerized game servers, built with a reactive state architecture and low-overhead background polling.

## Overview

Sovereign GameMaster provides a centralized control and observability layer over Docker containers. Rather than scattering container queries and polling loops across UI components, the application uses a single reactive registry to coordinate container discovery, selection state, runtime UI projections, and log retrieval.

## Key Features

- **Container Lifecycle Orchestration:** Create, start, stop, pause, unpause, restart, and remove containers via `DockerContainerManager` and a `ProcessExecutor` abstraction.
- **Diagnostics & Monitoring:** Query container status, inspection details, resource stats, and logs through `DockerMonitorManager`.
- **Reactive Single Source of Truth:** `ServerRegistry` polls container state and exposes `StateFlow` streams (including `servers`, `selectedServerId`, and `activeRuntimeServer`) with sticky selection across refresh cycles.
- **Log Filtering & Clear Markers:** `ContainerLogManager` retrieves logs using `--tail` and `--since` timestamps, supports per-container log clearing, and filters out configurable log noise.
- **Streaming Support:** `DockerProcessExecutor` includes line-by-line process streaming (`streamCommand`) alongside synchronous command execution.

## Architecture

- `com.sovereign.gamemaster.orchestration` — Low-level Docker CLI execution (`DockerProcessExecutor`, `DockerExecutionResult`), lifecycle actions (`DockerContainerManager`), and diagnostics/monitoring (`DockerMonitorManager`).
- `com.sovereign.gamemaster.core` — Central state coordination (`ServerRegistry`) and log handling (`ContainerLogManager`).
- `com.sovereign.gamemaster.model` — Domain and UI models including `ContainerConfig`, `ContainerState`, `ServerSummary`, and `ServerRuntimeUiModel`.
- `com.sovereign.gamemaster.viewmodel` & `com.sovereign.gamemaster.ui` — Dashboard state management and Compose UI components.

## Prerequisites

- **Docker** installed and running on the host machine.
- **JDK & Gradle** configured for building and running the Kotlin/Compose project.

## Roadmap

- Expand from single-host Docker container management toward **full cloud orchestration** across distributed/remote compute environments.
- Continue refining unified dashboard/server-selection UX and real-time log streaming.

## License

This project is licensed under the **GNU General Public License v3.0 (GPL-3.0)**.

Sovereign GameMaster is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License.