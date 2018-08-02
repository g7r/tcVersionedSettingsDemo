package name.sz.perProjectVersionedSettings.demo;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.*;

public class MyProjectSettingsTest {

    private static Element newSettingsXml(String internals) throws JDOMException, IOException {
        final Document doc = new SAXBuilder().build(
                new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "  <" + MyProjectSettings.ROOT_ELEMENT + ">\n" +
                        internals +
                        "  </" + MyProjectSettings.ROOT_ELEMENT + ">\n"));

        return doc.getRootElement();
    }

    @Test
    public void TestReadFromEmpty() throws JDOMException, IOException {
        final MyProjectSettings s = new MyProjectSettings("TEST");
        s.readFrom(newSettingsXml(""));

        assertEquals("", s.getMessage());
    }

    @Test
    public void TestReadFromWithMessage() throws JDOMException, IOException {
        final MyProjectSettings s = new MyProjectSettings("TEST");
        s.readFrom(newSettingsXml("<message>foo</message>"));

        assertEquals("foo", s.getMessage());
    }

    @Test
    public void TestWriteToEmpty() throws JDOMException, IOException {
        final Element root = newSettingsXml("");
        final MyProjectSettings s = new MyProjectSettings("TEST");
        s.writeTo(root);
        assertNull(root.getChild(MyProjectSettings.MESSAGE_ELEMENT));
    }

    @Test
    public void TestWriteToWithMessage() throws JDOMException, IOException {
        final Element root = newSettingsXml("");
        final MyProjectSettings s = new MyProjectSettings("TEST");
        s.setMessage("foo");
        s.writeTo(root);
        assertNotNull(root.getChild(MyProjectSettings.MESSAGE_ELEMENT));
        assertEquals("foo", root.getChild(MyProjectSettings.MESSAGE_ELEMENT).getText());
    }
}
