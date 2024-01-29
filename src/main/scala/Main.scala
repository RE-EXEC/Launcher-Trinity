package net.k3nder.launchertrinity

import javafx.application.Application
import javafx.application.Application.launch
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.layout.Pane
import javafx.stage.Stage
import net.k3nder.launchertrinity.controlers.Ccontroler
import net.k3nder.launchertrinity.settings.setting
import net.kender.Kjson.ConfigFile
import scalafx.application.{JFXApp, JFXApp3}
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{FXMLView, NoDependencyResolver}

import java.io.{File, FileReader, IOException}
import java.net.URL
import java.util

object Main extends JFXApp3 {
  override def start(): Unit = {
    StartSettings()
    val resource = new URL("file:///C:\\Users\\krist\\Desktop\\untitled\\src\\main\\scala\\controlers\\fxml\\pane.fxml");

    val root = FXMLView(resource, NoDependencyResolver)

    stage = new JFXApp3.PrimaryStage() {
      title = "Launcher Trinity"
      scene = new Scene(root)
    }
  }

  private def StartSettings(): Unit = {
    val file: File = new File("settings.json");
    val config: ConfigFile = new ConfigFile(file);

    val array: util.ArrayList[setting[?]] = new util.ArrayList[setting[?]];
    array.add(new setting[Boolean]("desc",false));

    val g = new settings.Group("name",array);
    config.putValue("settings",g);
    config.save()
  }
}