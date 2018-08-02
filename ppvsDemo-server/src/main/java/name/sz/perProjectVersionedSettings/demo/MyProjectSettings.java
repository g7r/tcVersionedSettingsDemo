package name.sz.perProjectVersionedSettings.demo;

import jetbrains.buildServer.serverSide.settings.ProjectSettings;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.jetbrains.annotations.NotNull;

import static name.sz.perProjectVersionedSettings.demo.Logger.LOGGER;

public class MyProjectSettings implements ProjectSettings {

    @NotNull
    static final String ROOT_ELEMENT = "mySettings";

    @NotNull
    static final String MESSAGE_ELEMENT = "message";

    @NotNull
    private final String projectId;

    private String message = "";

    MyProjectSettings(@NotNull String projectId){
        this.projectId = projectId;
    }

    @Override
    public void readFrom(Element rootElement)
    {
        LOGGER.info("readFrom invoked: projectId=" + projectId +
                "\n" + new XMLOutputter().outputString(rootElement.getDocument()));

        final Element messageElement = rootElement.getChild(MESSAGE_ELEMENT);
        if (messageElement != null) {
            LOGGER.info("Got <message> element");
            message = messageElement.getText();
        }
    }

    @Override
    public void writeTo(Element parentElement)
    {
        LOGGER.info("writeTo invoked: projectId=" + projectId);
        if (!message.isEmpty()) {
            final Element messageElement = new Element(MESSAGE_ELEMENT);
            messageElement.setText(message);
            parentElement.addContent(messageElement);
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String newMessage) {
        message = newMessage;
    }

    @Override
    public void dispose() {
        LOGGER.info("dispose invoked: projectId=" + projectId);
    }
}
