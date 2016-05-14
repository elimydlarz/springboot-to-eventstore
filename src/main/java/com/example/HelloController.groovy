package com.example

import com.google.gson.Gson
import com.mashape.unirest.http.Unirest
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

import javax.xml.bind.DatatypeConverter
import java.security.MessageDigest
import java.time.LocalTime


@RestController
class HelloController {

  @RequestMapping("/")
  index() {
    def eventInput = [currentTime: LocalTime.now().toString()]
    def eventOutput = Transformer.transform(eventInput)
    def eventJson = new Gson().toJson([eventOutput])

    Unirest.post('http://127.0.0.1:2113/streams/newstream')
        .header('content-type', 'application/json')
        .header('es-eventtype', 'SomeEvent')
        .header('ES-EventId', hash(eventJson))
        .body(eventJson)
        .asStringAsync()

    "I'm on it!"
  }

  private hash(input) {
    DatatypeConverter.printHexBinary(
        MessageDigest.getInstance('MD5').digest(input.getBytes('UTF-8'))
    )
  }
}
