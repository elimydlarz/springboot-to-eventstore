package com.example

import com.google.gson.Gson
import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.exceptions.UnirestException
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

import javax.xml.bind.DatatypeConverter
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.time.LocalTime

@RestController
class HelloController {

  @RequestMapping("/")
  def index() throws UnirestException, NoSuchAlgorithmException, UnsupportedEncodingException {
    def eventJson = new Gson().toJson([
        [time: LocalTime.now().toString()]
    ])

    def response = Unirest.post('http://127.0.0.1:2113/streams/newstream')
        .header('content-type', 'application/json')
        .header('es-eventtype', 'SomeEvent')
        .header('ES-EventId', getHash(eventJson))
        .body(eventJson)
        .asString()

    String.valueOf(response.getStatus())
  }

  private def getHash(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    DatatypeConverter.printHexBinary(
        MessageDigest.getInstance('MD5').digest(input.getBytes('UTF-8'))
    )
  }

}
