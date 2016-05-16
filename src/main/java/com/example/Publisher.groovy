package com.example

import com.google.gson.Gson
import com.mashape.unirest.http.Unirest

import javax.xml.bind.DatatypeConverter
import java.security.MessageDigest

class Publisher {

  static publish(event) {
    def eventJson = new Gson().toJson([event])

    Unirest.post('http://127.0.0.1:2113/streams/newstream')
        .header('content-type', 'application/json')
        .header('es-eventtype', 'SomeEvent')
        .header('ES-EventId', hash(eventJson))
        .body(eventJson)
        .asStringAsync()
  }

  static private hash(input) {
    DatatypeConverter.printHexBinary(
        MessageDigest.getInstance('MD5').digest(input.getBytes('UTF-8'))
    )
  }
}
