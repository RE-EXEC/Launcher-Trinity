
package net.k3nder.lt.json.parser

import com.fasterxml.jackson.annotation.{JsonIgnore, JsonProperty}
import com.fasterxml.jackson.databind.ObjectMapper

class profile {
  @JsonProperty("created") var created: String = _
  @JsonProperty("icon") var icon: String = _
  @JsonProperty("lastUsed") var lastUsed: String = _
  @JsonProperty("lastVersionId") var lastVersionId: String = _
  @JsonProperty("name") var name: String = _
  @JsonProperty("javaArgs") var javaArgs: String = _
  @JsonProperty("javaDir") var javaDir: String = _
  @JsonProperty("gameDir") var gameDir: String = _
  @JsonProperty("Type") var Type: String = _
  @JsonIgnore @JsonProperty("resolution") var resolution: resolution = _
  def toJson(): String = {
    new ObjectMapper().writeValueAsString(this)
  }

  override def toString: String = {
    name;
  }
}
