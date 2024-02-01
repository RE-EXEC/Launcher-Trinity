package net.k3nder.launchertrinity
package controlers

import javafx.scene.Node
import scalafxml.core.{FXMLView, NoDependencyResolver}

import java.net.URL

class CPane(fxml: URL){
  def getNODE(): Node = {
    return FXMLView(fxml, NoDependencyResolver)
  }
}
