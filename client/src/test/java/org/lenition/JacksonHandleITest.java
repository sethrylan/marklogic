package org.lenition;


import com.google.common.io.Resources;
import com.google.gson.JsonElement;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.JSONDocumentManager;
import com.marklogic.client.extra.gson.GSONHandle;
import groovy.util.ConfigSlurper;
import org.apache.commons.codec.Charsets;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class JacksonHandleITest {

    private static Log LOG = LogFactory.getLog(JacksonHandleITest.class);

    Map config;
    MarkLogicClient client;

    @Before
    public void before() {
        Class<?> configClass = null;
        try {
            configClass = this.getClass().getClassLoader().loadClass("Config");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        config = new ConfigSlurper().parse(configClass).flatten();

        client = new MarkLogicClientFactory().createClient((String)config.get("markLogic.host"), (Integer)config.get("markLogic.port"), (String)config.get("markLogic.user"), (String)config.get("markLogic.password"), (DatabaseClientFactory.Authentication)config.get("markLogic.authentication"));
    }


    @Test
    public void gsonHandleTest() throws IOException {

        String filename = "data/observation-example.json";

        // create the client
        DatabaseClient client = DatabaseClientFactory.newClient(
                (String)config.get("markLogic.host"), (Integer)config.get("markLogic.port"), (String)config.get("markLogic.user"), (String)config.get("markLogic.password"), (DatabaseClientFactory.Authentication)config.get("markLogic.authentication"));

        // create a manager for JSON documents
        JSONDocumentManager docMgr = client.newJSONDocumentManager();

        // read the example file
        URL url = Resources.getResource(filename);
        String json = Resources.toString(url, Charsets.UTF_8);

        // create an identifier for the document
        String docId = "/json/" + filename;

        // create a handle for the document
        GSONHandle writeHandle = new GSONHandle();

        // parse the example file into a Jackson JSON structure
        JsonElement writeDocument = writeHandle.getParser().parse(json);
        writeHandle.set(writeDocument);

        // write the document
        docMgr.write(docId, writeHandle);

        // create a handle to receive the document content
        GSONHandle readHandle = new GSONHandle();

        // read the document content
        docMgr.read(docId, readHandle);

        // access the document content
        JsonElement readDocument = readHandle.get();

        assertNotNull(readDocument);

        assertEquals("Observation", readDocument.getAsJsonObject().get("resourceType").getAsString());
        assertEquals(readDocument.toString(), this.client.getJsonString(docId));

        // delete the document
        docMgr.delete(docId);

        // release the client
        client.release();
    }

}