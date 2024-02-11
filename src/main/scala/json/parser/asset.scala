package net.k3nder.lt
package json.parser

import com.fasterxml.jackson.annotation.JsonProperty

class asset {
  @JsonProperty("file") var file: String = _
  @JsonProperty("url") var url: String = _
}
