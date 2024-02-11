package net.k3nder.launchertrinity
package settings

import com.fasterxml.jackson.annotation.{JsonCreator, JsonGetter, JsonProperty}

@JsonCreator
class setting[T](@JsonProperty("description") description: String,@JsonProperty("value") defaultValue: T){
  private var value: T = defaultValue;
  def set(Value: T): Unit = {
    this.value = Value;
  }
  @JsonGetter("value")
  def get(): T = {
    return value;
  }
  @JsonGetter("description")
  def getDescription(): String = {
    return description
  }
}
