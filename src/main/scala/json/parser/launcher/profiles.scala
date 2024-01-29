package net.k3nder.launchertrinity
package json.parser.launcher

import com.fasterxml.jackson.annotation.{JsonCreator, JsonGetter, JsonProperty}

import java.util

@JsonCreator
class profiles(@JsonProperty("profiles") Profiles: util.ArrayList[json.parser.profile]) {
  private var profiles: util.ArrayList[json.parser.profile] = Profiles
  @JsonGetter("profiles")
  def getProfiles(): util.ArrayList[json.parser.profile] = {
    return profiles
  }
}
