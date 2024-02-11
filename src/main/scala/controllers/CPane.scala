package net.k3nder.lt.controllers

import javafx.scene.Node
import javafx.scene.layout.Pane
import scalafxml.core.{FXMLView, NoDependencyResolver}

import java.net.URL
import java.util

class CPane(pane: Pane, habilite_closes: Boolean){
  if(habilite_closes) CPane.noClosedPanes.add(this)
  private var onOpen: Runnable = () => {}
  var isOpen: Boolean = false;
  Open(false)
  def Open(bool: Boolean): Unit = {
    pane.setVisible(bool)
    pane.setDisable(!bool)
    getOnOpen().run()
    isOpen = bool;
  }
  def open(boolean: Boolean): Unit = {
    println("pane: " + boolean)
    Open(boolean)
    closeOther()
  }
  private def closeOther(): Unit = {
    CPane.noClosedPanes.stream()
      .filter(!_.eq(this))
      .forEach(_.Open(false))
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
