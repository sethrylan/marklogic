package org.lenition
import com.google.gson.JsonElement
import com.marklogic.client.DatabaseClient
import com.marklogic.client.document.JSONDocumentManager
import com.marklogic.client.document.XMLDocumentManager
import com.marklogic.client.extra.gson.GSONHandle
import com.marklogic.client.io.DOMHandle
import com.marklogic.client.io.InputStreamHandle
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.w3c.dom.Document

class MarkLogicClient {
    private static Log LOG = LogFactory.getLog(MarkLogicClient)

    private final DatabaseClient client
    private final XMLDocumentManager xmlDocumentManager
    private final JSONDocumentManager jsonDocumentManager

    MarkLogicClient(DatabaseClient client) {
        this.client = client
        xmlDocumentManager = client.newXMLDocumentManager()
        jsonDocumentManager = client.newJSONDocumentManager()
    }

    void put(InputStream is, String docId) {
        InputStreamHandle writeHandle = new InputStreamHandle()
        writeHandle.set(new ByteArrayInputStream(xml.getBytes("UTF-8")))
        xmlDocumentManager.write(docId, writeHandle)

    }

    void putXml(String xml, String docId) {
        DOMHandle writeHandle = new DOMHandle()
        def doc = groovy.xml.DOMBuilder.newInstance().parseText(xml)
        writeHandle.set(doc);
        xmlDocumentManager.write(docId, writeHandle);
    }

    /**
     * Returns the XML DOM document for a given document ID, or null
     * @param docId
     * @return
     */
    Document getXmlDocument(String docId) {
        if (LOG.debugEnabled) {
            LOG.debug("Getting XML document with docId $docId")
        }

        final DOMHandle handle = new DOMHandle()
        try {
            xmlDocumentManager.read(docId, handle)
        } catch (com.marklogic.client.ResourceNotFoundException e) {
            return null
        }
        handle.get()
    }

    void putJson(String json, String docId) {
        GSONHandle writeHandle = new GSONHandle();
        JsonElement writeDocument = writeHandle.getParser().parse(json);
        writeHandle.set(writeDocument);
        jsonDocumentManager.write(docId, writeHandle);
    }

    void delete(String docId) {
        jsonDocumentManager.delete(docId);
    }

    /**
     * Returns the JSON string for a given document ID, or null
     * @param docId
     * @return
     */
    String getJsonString(String docId) {
        if (LOG.debugEnabled) {
            LOG.debug("Getting JSON document with docId $docId")
        }

        final GSONHandle handle = new GSONHandle()
        try {
            jsonDocumentManager.read(docId, handle)
        } catch (com.marklogic.client.ResourceNotFoundException e) {
            return null
        }
        JsonElement readDocument = handle.get()
        readDocument.toString()
    }

    void release() {
        client.release()
    }

    private Document toDocument(String xml) {
        def doc = groovy.xml.DOMBuilder.newInstance().parseText(xml)
        doc.documentElement as String
    }
}
