package net.k3nder.launchertrinity
package json.parser

import com.fasterxml.jackson.annotation.JsonProperty

class profile {
  var created: String = _
  var icon: String = _
  var lastUsed: String = _
  var lastVersionId: String = _
  var name: String = _
  @JsonProperty("type") var Type: String = _
  var javaArgs: String = _
  var javaDir: String = _
  var gameDir: String = _
}
