package net.k3nder.launchertrinity
package controlers

import javafx.scene.Node
import javafx.scene.layout.Pane
import scalafxml.core.{FXMLView, NoDependencyResolver}

import java.net.URL
import java.util

class CPane(pane: Pane){
  CPane.noClosedPanes.add(this)
  private var onOpen: Runnable = () => {}
  private def Open(bool: Boolean): Unit = {
    pane.setVisible(bool)
    pane.setDisable(!bool)
    onOpen.run()
  }
  def open(boolean: Boolean): Unit = {
    Open(boolean)
    closeOther()
  }
  def closeOther(): Unit = {
    val convertedScalaList: List[CPane] = CPane.noClosedPanes.toArray(Array[CPane]()).toList
    for(pane: CPane <- convertedScalaList) {
      if(!pane.equals(this)) pane.Open(false)
    }
  }
  def setOnOpen(on: Runnable): Unit = {
    onOpen = on
  }
  def getOnOpen(): Runnable = {
    return onOpen
  }
}
object CPane{
  var noClosedPanes: util.ArrayList[CPane] = new util.ArrayList[CPane]()
}
