
package net.k3nder.lt.controllers

import javafx.animation.FillTransition
import javafx.scene.control.Button
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Text
import javafx.util.Duration

import java.util

object Indicator {
  // Variable estática (companion object)
  var noSelectedList: util.ArrayList[Indicator] = new util.ArrayList[Indicator]()
  def LinkIndicator(button: Text,indicator: Indicator): Unit = {
    setIndicator(button,indicator)
  }
  private def setIndicator(text: Text, indi: Indicator): Unit = {
    val fillTransition: FillTransition = new FillTransition(Duration.millis(10), indi.getRect())
    // Establecer el color de inicio y el color de destino
    fillTransition.setFromValue(Color.TRANSPARENT)
    fillTransition.setToValue(Color.CYAN)
    text.setOnMouseClicked((event: MouseEvent) => {
      fillTransition.play()
      indi.getOnAction().run()
      val convertedScalaList: List[Indicator] = Indicator.noSelectedList.toArray(Array[Indicator]()).toList
      for(indic: Indicator <- convertedScalaList){
        if(!indic.equals(indi)){
          indic.set(false)
        }
      }

    })

  }
}
class Indicator(rect: Rectangle){
  rect.setFill(Color.TRANSPARENT)
  Indicator.noSelectedList.add(this)
  private var OnAction: Runnable = () => {};
  def getRect(): Rectangle = {return rect}
  def setOnAction(run: Runnable): Unit = {OnAction = run}
  def getOnAction(): Runnable = {return OnAction}
  def set(cond: Boolean): Unit = {
    rect.setFill(if (cond) Color.CYAN else Color.TRANSPARENT)
  }
}


