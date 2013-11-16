import com.marklogic.client.DatabaseClientFactory

markLogic {
    host = '10.2.3.7'
    port = 8003
    user = 'admin'
    password = 'admin'
    authentication = DatabaseClientFactory.Authentication.DIGEST
}