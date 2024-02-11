package net.k3nder.launchertrinity
package settings

import com.fasterxml.jackson.annotation.{JsonCreator, JsonGetter, JsonProperty}

import java.util

@JsonCreator
class Group(@JsonProperty("name") groupname: String,@JsonProperty("settings") Settings: util.ArrayList[setting[?]]) {
  var name: String = groupname;
  var settings: util.ArrayList[setting[?]] = Settings;

  @JsonGetter("settings")
  def getSettings(): util.ArrayList[setting[?]] = {
    return settings;
  }
  @JsonGetter("name")
  def getName(): String = {
    return name;
  }
}
