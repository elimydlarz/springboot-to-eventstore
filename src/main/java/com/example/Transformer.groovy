package com.example

class Transformer {

  static addTime = {
    input, output ->
      output['time'] = input['currentTime']
      output
  }

  static transforms = [
      addTime
  ]

  static transform(input) {
    transforms.inject([:]) { output, transform -> transform.call(input, output) }
  }
}
