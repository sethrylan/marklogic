package org.lenition

import com.google.gson.Gson
import com.google.gson.JsonElement
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.junit.Before
import org.junit.Test
import org.w3c.dom.Document

import static org.junit.Assert.*

class MarkLogicClientITest {
    private static Log LOG = LogFactory.getLog(MarkLogicClientITest)

    MarkLogicClient client

    @Before
    void before() {
        final Class<?> configClass = this.class.classLoader.loadClass('Config')
        final ConfigObject config = new ConfigSlurper().parse(configClass)

        final MarkLogicClientFactory factory = new MarkLogicClientFactory().with {
            host = config.markLogic.host
            port = config.markLogic.port
            user = config.markLogic.user
            password = config.markLogic.password
            authentication = config.markLogic.authentication

            it
        }

        client = factory.createClient()
    }

    @Test
    void shouldGetXmlDocument() {
        def filename = "/data/observation-example.xml"

        client.delete(filename)

        assertNull(client.getXmlDocument(filename))

        client.putXml(this.class.getResource(filename).text, filename)
        Document document = client.getXmlDocument(filename)

        assertNotNull(document)
        assertEquals("Observation", document.getDocumentElement().getTagName());

    }

    @Test
    void shouldGetJsonDocument() {
        def filename = "/data/observation-example.json"

        client.delete(filename)

        assertNull(client.getJsonString(filename))

        client.putJson(this.class.getResource(filename).text, filename)
        String json = client.getJsonString(filename)

        assertNotNull(json)

        Gson gson = new Gson();
        JsonElement readDocument = gson.fromJson(json, JsonElement.class);

        assertEquals("Observation", readDocument.getAsJsonObject().get("resourceType").getAsString());

    }
}
