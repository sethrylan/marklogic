

Setup
==

Set up a new REST API at http://10.2.3.7:8000/appservices
   -> Select Database -> Configure -> Add REST API Instance (servername = marklogic, port = 8003)

or

```
cat << 'EOF' > rest_service.config
{
  "rest-api": {
    "name": "marklogic-rest",
    "database": "Documents",
    "port": "8003"
  }
}
EOF

curl --anyauth --user admin:admin -X POST -d@'./rest_service.config' -i -H "Content-type: application/json" http://10.2.3.7:8002/v1/rest-apis
```

Build and Run
==
```gradle build``` or ```gradle itest```


### Create XML doc:

```
curl -v --digest --user admin:admin -H "Content-Type: application/xml" -X PUT -d '<note></note>' "http://10.2.3.7:8003/v1/documents?uri=/xml/test.xml"
curl -v --digest --user admin:admin -H "Content-Type: application/xml" -X PUT -T "client/src/test/resources/data/observation-example.xml" "http://10.2.3.7:8003/v1/documents?uri=/xml/observation.xml"
```

### View XML doc:
```
GET http://10.2.3.7:8003/v1/documents?uri=/xml/observation.xml
```

### Delete doc:
```
curl -v --digest --user admin:admin -H "Content-Type: application/xml" -X DELETE "http://10.2.3.7:8003/v1/documents?uri=/json/observation.json"
```


### More ML examples:
https://github.com/adamfowleruk/mljavasamples
https://github.com/adamfowleruk/mlperf/tree/941793ff02e838bcd2fef51b71478a78f312c4e6

### XQuery examples:
https://github.com/robwhitby/xray

###Example data
http://www.hl7.org/implement/standards/fhir/integrated-examples.html
