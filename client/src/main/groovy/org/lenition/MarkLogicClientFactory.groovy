package org.lenition

import com.marklogic.client.DatabaseClientFactory
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

class MarkLogicClientFactory {
    private static Log LOG = LogFactory.getLog(MarkLogicClientFactory)

    String host
    int port
    String user
    String password
    DatabaseClientFactory.Authentication authentication

    MarkLogicClient createClient() {
        if (LOG.debugEnabled) {
            LOG.debug("Creating client for $host:$port")
        }
        createClient(host, port, user, password, authentication)
    }

    MarkLogicClient createClient(String host, int port, String user, String password, DatabaseClientFactory.Authentication authentication) {
        if (LOG.debugEnabled) {
            LOG.debug("Creating client for $host:$port")
        }
        new MarkLogicClient(DatabaseClientFactory.newClient(host, port, user, password, authentication))
    }
}