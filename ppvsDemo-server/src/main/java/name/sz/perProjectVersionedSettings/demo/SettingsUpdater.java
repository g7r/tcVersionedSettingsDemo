package name.sz.perProjectVersionedSettings.demo;

import jetbrains.buildServer.serverSide.BuildServerAdapter;
import jetbrains.buildServer.serverSide.ProjectManager;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.serverSide.SProject;
import jetbrains.buildServer.serverSide.settings.ProjectSettings;
import jetbrains.buildServer.serverSide.settings.ProjectSettingsManager;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;

import static name.sz.perProjectVersionedSettings.demo.Logger.LOGGER;

public class SettingsUpdater extends BuildServerAdapter {

    @NotNull
    private final ProjectSettingsManager psm;

    @NotNull
    private final ProjectManager pm;

    @NotNull
    private final SBuildServer buildServer;

    public SettingsUpdater(@NotNull ProjectSettingsManager psm, @NotNull ProjectManager pm, @NotNull SBuildServer buildServer) {
        this.psm = psm;
        this.pm = pm;
        this.buildServer = buildServer;
    }

    public void register() {
        LOGGER.info("Registering build server listener");
        buildServer.addListener(this);
    }

    @Override
    public void serverStartup() {
        LOGGER.info("Updating settings for all projects");
        for (SProject project : pm.getActiveProjects()) {
            final ProjectSettings rawSettings = psm.getSettings(project.getProjectId(), MyProjectSettings.ROOT_ELEMENT);
            final MyProjectSettings settings = (MyProjectSettings)rawSettings;

            LOGGER.info("Loaded settings" +
                    ": projectId=" + project.getProjectId() +
                    ", projectExternalId=" + project.getExternalId() +
                    ", message=" + settings.getMessage());

            LOGGER.info("Setting message: projectId=" + project.getProjectId() + ", projectExternalId=" + project.getExternalId());
            settings.setMessage(project.getExternalId() + "_" + DateTime.now().toString());

            project.persist();
        }
    }
}
