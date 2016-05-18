package com.example;

import au.com.dius.pact.consumer.*;
import au.com.dius.pact.consumer.groovy.PactBuilder
import org.junit.Test;

class PollerTest {

  @Test
  void 'contract'() {

    def someProvider = new PactBuilder()

    someProvider {
      serviceConsumer 'Engine'
      hasPactWith 'SomeProvider'
      port 2113

      given 'everything is fine'
      uponReceiving 'a request for data'
      withAttributes(
          method: 'get',
          path: '/streams/oldstream/3',
          headers: [Accept: 'application/json']
      )
      willRespondWith(
          status: 200,
          body: "[{\"currentTime\": \"14:52:57.276\"}]"
      )
    }

    VerificationResult result = someProvider.run() {
      Poller.poll({ eventSource -> "Yay!" });
      sleep(1000) // Sorry!
    }

    assert result == PactVerified$.MODULE$
  }
}