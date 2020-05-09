# NeoCraft

Magic maketh Machine

*or some other tagline*

## Setting up your development environment

### Eclipse

Clone this project (from the 1.15.2-dev branch) and cd into the directory.

Run the following setup commands:
```bash
./gradlew eclipse
./gradlew genEclipseRuns
```

After that, startup Eclipse, import an existing Gradle project, and select the git repository.

Navigate to Run Configurations and launch `runClient`.
