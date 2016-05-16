package com.example

import com.google.gson.Gson
import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.JsonNode
import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.async.Callback
import com.mashape.unirest.http.exceptions.UnirestException
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

import javax.xml.bind.DatatypeConverter
import java.security.MessageDigest

@RestController
class HelloController {

  @RequestMapping('/')
  index() {
     Unirest.get('http://localhost:2113/streams/oldstream/1')
        .header('Accept', 'application/json')
        .asJsonAsync(new Callback<JsonNode>() {
            def failed(UnirestException e) {}
            def cancelled() {}

            def completed(HttpResponse<JsonNode> response) {
                def event = Transformer.transform(response.getBody().getArray().get(0))
                publish(event)
            }
        });

    "I'm on it!"
  }

  private publish(event) {
    def eventJson = new Gson().toJson([event])

    Unirest.post('http://127.0.0.1:2113/streams/newstream')
        .header('content-type', 'application/json')
        .header('es-eventtype', 'SomeEvent')
        .header('ES-EventId', hash(eventJson))
        .body(eventJson)
        .asStringAsync()
  }

  private hash(input) {
    DatatypeConverter.printHexBinary(
        MessageDigest.getInstance('MD5').digest(input.getBytes('UTF-8'))
    )
  }
}
