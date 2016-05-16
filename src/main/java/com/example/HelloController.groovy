package com.example

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
class HelloController {

  @RequestMapping('/')
  index() {
    Poller.poll({
      eventSource ->
        def event = Transformer.transform(eventSource, Transforms.EVENT)
        Publisher.publish(event)
    })

    "I'm on it!"
  }
}
