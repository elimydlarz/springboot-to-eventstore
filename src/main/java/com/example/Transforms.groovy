package com.example

class Transforms {

  private static addTime = {
    input, output ->
      output['time'] = input['currentTime']
      output
  }

  static EVENT = [
      addTime
  ]
}
