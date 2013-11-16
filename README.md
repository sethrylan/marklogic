

Setup
==

TODO: Automate using [Service Management API](http://docs.marklogic.com/REST/client/service-management)

Set up a new REST API at http://10.2.3.7:8000/appservices
   -> Select Database -> Configure -> Add REST API Instance (servername = marklogic, port = 8003)


Example data from http://www.hl7.org/implement/standards/fhir/integrated-examples.html

Create XML doc:
```
curl -v --digest --user admin:admin -H "Content-Type: application/xml" -X PUT -d '<note></note>' "http://10.2.3.7:8003/v1/documents?uri=/xml/test.xml"
curl -v --digest --user admin:admin -H "Content-Type: application/xml" -X PUT -T "client/src/test/resources/data/observation-example.xml" "http://10.2.3.7:8003/v1/documents?uri=/xml/observation.xml"
```

View XML doc:
```
GET http://10.2.3.7:8003/v1/documents?uri=/xml/observation.xml
```

Delete XML doc:
```
curl -v --digest --user admin:admin -H "Content-Type: application/xml" -X DELETE "http://10.2.3.7:8003/v1/documents?uri=/xml/test.xml"
```


More ML examples:
https://github.com/adamfowleruk/mljavasamples
https://github.com/adamfowleruk/mlperf/tree/941793ff02e838bcd2fef51b71478a78f312c4e6

XQuery examples:
https://github.com/robwhitby/xray