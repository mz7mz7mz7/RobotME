package org.robotme.xmlparser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Marcin Zduniak
 */
public class Binary2XmlScenarioConverterTest {

    private InputStream is;

    @Before
    public void setUp() throws Exception {
        is = getClass().getResourceAsStream("/example-binary-scenario.rme");
    }

    /**
     * Test method for {@link org.robotme.xmlparser.Binary2XmlScenarioConverter#convert(java.io.InputStream)}.
     * 
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    @Test
    public void testConvert() throws IOException, ParserConfigurationException, SAXException {
        final String xml = Binary2XmlScenarioConverter.convert(is);
        System.out.println(xml);
        assertNotNull(xml);
        assertTrue(xml.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<scenario>\n\n"));
        assertTrue(xml.endsWith("</scenario>\n"));

        assertTrue("XML must be well formed", isXmlWellFormed(xml));
    }

    private boolean isXmlWellFormed(String xml) throws IOException, ParserConfigurationException, SAXException {
        // init parser:
        SAXParserFactory spfactory = SAXParserFactory.newInstance();
        SAXParser saxParser = spfactory.newSAXParser();
        DefaultHandler dh = new DefaultHandler();
        // parse the XML document using SAX parser:
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
            saxParser.parse(bais, dh); // SAXException, IOException
        } catch (SAXException se) {
            return false;
        }
        return true;
    }

}
