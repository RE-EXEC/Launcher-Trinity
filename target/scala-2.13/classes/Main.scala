package net.k3nder.lt

import javafx.application.Application
import javafx.application.Application.launch
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.layout.Pane
import javafx.stage.Stage
import controllers.{Controller, Log}

import com.fasterxml.jackson.databind.ObjectMapper
import javafx.scene.image.Image
import net.k3nder.lt.json.parser.{LAssetIndex, asset}
import net.kender.Kjson.ConfigFile
import net.kender.kutils.OperativeSystem
import net.kender.logger.log5k.Logger
import net.kender.logger.log5k.conf.log5kConf
import org.apache.commons.io.FileUtils
import scalafx.application.{JFXApp, JFXApp3}
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{FXMLView, NoDependencyResolver}

import java.io.{File, FileReader, FileWriter, IOException}
import java.net.URL
import java.nio.file.{Path, Paths}
import java.util
import scala.jdk.CollectionConverters.IterableHasAsScala

object Main extends JFXApp3 {
  override def start(): Unit = {
    Logger.config = new log5kConf(getClass.getResourceAsStream("controllers/logger.properties"));
    
    prepareFiles()
    
    val resource = getClass.getResource("controllers/fxml/pane.fxml")

    val root = FXMLView(resource, NoDependencyResolver)

    stage = new JFXApp3.PrimaryStage() {
      title = "Launcher Trinity"
      scene = new Scene(root)
      resizable = false
    }
    stage.getIcons.add(new Image("https://iili.io/HZpq1xS.png"))
  }
  private def prepareFiles(): Unit = {
    val log: Logger = new Log(Main.getClass)
    val username: String = System.getProperty("user.name")
    val Operative: OperativeSystem = OperativeSystem.thisOperativeSystem();
    val FilesDirectory: Path = Paths.get(String.format(if (Operative == OperativeSystem.Linux) "/home/%s/lt/" else "C:/Users/%s/Documents/.LT",username))
    log.data("operative", Operative)
    log.data("files path", FilesDirectory)
    if(!FilesDirectory.toFile.exists()) FilesDirectory.toFile.mkdir()
    var index: LAssetIndex = new ObjectMapper().readValue(getClass.getResourceAsStream("/net/k3nder/lt/assets/index.json"), classOf[LAssetIndex])
    for(asset: asset <- index.files.asScala){
      log.data("file",asset.file + " " + FilesDirectory + "/assets/" + asset.file)
      FileUtils.copyURLToFile(new URL(asset.url),new File(FilesDirectory + "/assets/" + asset.file))
    }
    FileUtils.copyURLToFile(new URL(index.configuration), new File(FilesDirectory + "/assets/index.json"))
    createNewSettingsFile("\"profiles\":[]","trinity.profiles.json")
    createNewSettingsFile("\"quickplays\":[]","trinity.quickplays.json")
    createNewSettingsFile("","trinity.settings.json")
  }
  private def createNewSettingsFile(initialObject: String,filename: String): Unit = {
    val username: String = System.getProperty("user.name")
    val Operative: OperativeSystem = OperativeSystem.thisOperativeSystem();
    val FilesDirectory: Path = Paths.get(String.format(if (Operative == OperativeSystem.Linux) "/home/%s/lt/" else "C:/Users/%s/Documents/.LT",username))
    val SettingsFile: File = new File(String.format(FilesDirectory + "\\%s",filename))
    if(!SettingsFile.exists()) {
      SettingsFile.createNewFile()
      val writer = new FileWriter(SettingsFile)
      writer.write(String.format("{%s}",initialObject))
      writer.close()
    }
  }
}