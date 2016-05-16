package com.example

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration

import static org.junit.Assert.assertEquals

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
class TransformerTests {

  @Test
  void addsTime() {
    def input = [currentTime: '14:52:57.276']
    def output = Transformer.transform(input, Transforms.EVENT);

    assertEquals(input['currentTime'], output['time'])
  }

}
