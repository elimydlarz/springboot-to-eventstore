package com.example

import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.JsonNode
import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.async.Callback
import com.mashape.unirest.http.exceptions.UnirestException

class Poller {

  static poll(onSuccess) {
    Unirest.get('http://localhost:2113/streams/oldstream/3')
    .header('Accept', 'application/json')
    .asJsonAsync(new Callback<JsonNode> () {
      void failed(UnirestException e) { System.out.println("The request has failed") }
      void cancelled() { System.out.println('The request has been cancelled') }

      void completed(HttpResponse<JsonNode> response) {
        onSuccess.call(response.getBody().getArray().get(0))
      }
    });
  }
}
