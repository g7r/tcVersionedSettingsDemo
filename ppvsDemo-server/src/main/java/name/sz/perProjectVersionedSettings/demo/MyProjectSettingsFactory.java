package name.sz.perProjectVersionedSettings.demo;

import jetbrains.buildServer.serverSide.ProjectManager;
import jetbrains.buildServer.serverSide.settings.ProjectSettings;
import jetbrains.buildServer.serverSide.settings.ProjectSettingsFactory;
import jetbrains.buildServer.serverSide.settings.ProjectSettingsManager;
import org.jetbrains.annotations.NotNull;

import static name.sz.perProjectVersionedSettings.demo.Logger.LOGGER;

public class MyProjectSettingsFactory implements ProjectSettingsFactory {

    @NotNull
    private final ProjectManager pm;

    public MyProjectSettingsFactory(
            @NotNull ProjectManager pm,
            @NotNull ProjectSettingsManager psm
    ) {
        this.pm = pm;

        LOGGER.info("Registering settings factory");
        psm.registerSettingsFactory(MyProjectSettings.ROOT_ELEMENT, this);
    }

    @NotNull
    @Override
    public ProjectSettings createProjectSettings(String projectId) {
        final String externalId = pm.findProjectById(projectId).getExternalId();
        LOGGER.info("Creating new project settings" +
                ": projectId=" + projectId +
                ", externalProjectId=" + externalId);
        return new MyProjectSettings(externalId);
    }
}
