package net.k3nder.lt
package json.parser

import com.fasterxml.jackson.annotation.JsonProperty

import java.util

class LAssetIndex {
  @JsonProperty("files") var files: util.ArrayList[asset] = _
  @JsonProperty("configuration") var configuration: String = _
}
