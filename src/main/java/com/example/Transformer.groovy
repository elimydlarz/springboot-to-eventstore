package com.example

class Transformer {

  static transform(input, transforms) {
    transforms.inject([:]) { output, transform -> transform.call(input, output) }
  }
}
