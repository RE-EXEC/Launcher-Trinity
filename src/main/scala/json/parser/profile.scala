package net.k3nder.launchertrinity
package json.parser

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper

class profile {
  @JsonProperty("created") var created: String = _
  @JsonProperty("icon") var icon: String = _
  @JsonProperty("lastUsed") var lastUsed: String = _
  @JsonProperty("lastVersionId") var lastVersionId: String = _
  @JsonProperty("name") var name: String = _
  @JsonProperty("type") var Type: String = _
  @JsonProperty("javaArgs") var javaArgs: String = _
  @JsonProperty("javaDir") var javaDir: String = _
  @JsonProperty("gameDir") var gameDir: String = _
  def toJson(): String = {
    return new ObjectMapper().writeValueAsString(this)
  }
}
