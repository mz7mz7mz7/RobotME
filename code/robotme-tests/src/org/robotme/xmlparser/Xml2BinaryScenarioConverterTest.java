package org.robotme.xmlparser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Marcin Zduniak
 */
public class Xml2BinaryScenarioConverterTest {

    private InputStream is;

    @Before
    public void setUp() throws Exception {
        is = getClass().getResourceAsStream("/scenario.xml");
    }

    /**
     * Test method for {@link org.robotme.xmlparser.Xml2BinaryScenarioConverter#convert(java.io.InputStream)}.
     * 
     * @throws IOException
     */
    @Test
    public void testConvert() throws IOException {
        final byte[] bytes = Xml2BinaryScenarioConverter.convert(is);
        assertNotNull(bytes);
        assertTrue(bytes.length > 0);

//        FileOutputStream fos = new FileOutputStream("c:/tmp/scenario.rme");
//        fos.write(bytes);
//        fos.close();
    }

}
