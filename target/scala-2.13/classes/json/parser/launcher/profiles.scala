
package net.k3nder.lt.json.parser.launcher

import com.fasterxml.jackson.annotation.{JsonCreator, JsonGetter, JsonProperty}

import java.util

@JsonCreator
class profiles(@JsonProperty("profiles") Profiles: util.ArrayList[net.k3nder.lt.json.parser.profile]) {
  private var profiles: util.ArrayList[net.k3nder.lt.json.parser.profile] = Profiles
  @JsonGetter("profiles")
  def getProfiles(): util.ArrayList[net.k3nder.lt.json.parser.profile] = {
    return profiles
  }
}
