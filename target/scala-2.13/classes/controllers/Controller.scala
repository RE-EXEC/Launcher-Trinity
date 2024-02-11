package net.k3nder.lt.controllers

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control._
import javafx.scene.image.{Image, ImageView}
import javafx.scene.layout
import javafx.scene.layout._
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.{Font, Text}
import net.k3nder.lt.json.parser.profile
import net.kender.Kjson.ConfigFile
import net.kender.core.mc
import net.kender.core.mc.EXTRAS.EXTRAS
import net.kender.core.mc.{Mc, Vmc, Vtype}
import net.kender.kutils.OperativeSystem
import scalafx.collections.ObservableBuffer
import scalafxml.core.macros.sfxml

import java.io.{File, FileReader, IOException, OutputStream, PrintStream}
import java.nio.file.{Path, Paths}
import java.util
import java.util.Properties
import javax.swing.JOptionPane
import scala.jdk.CollectionConverters._

@sfxml
class Controller(@FXML private var imageView: ImageView,
                 @FXML private var MENU_PANE: Pane,
                 @FXML private var VERSION_NUMBER: Text,
                 @FXML private var SETTINGS_BUTTON_PANE: Pane,
                 @FXML private var SETTINGS_ICON: ImageView,
                 @FXML private var SETTINGS_TEXT: Text,
                 @FXML private var OPTIONS_MENU: Pane,
                 @FXML private var TEXT_PLAY: Text,
                 @FXML private var TEXT_INSTALATIONS: Text,
                 @FXML private var TEXT_SKINS: Text,
                 @FXML private var TEXT_UPDATE_PATCH: Text,
                 @FXML private var SCROLL_PANE_GAME: ScrollPane,
                 @FXML private var PANE_GAME: Pane,
                 @FXML private var IMG_GAME: ImageView,
                 @FXML private var BOTTOM_PANE: Pane,
                 @FXML private var PLAY_BUTTON: Button,
                 @FXML private var PLAY_INDICATOR: Rectangle,
                 @FXML private var INSTALL_INDICATOR: Rectangle,
                 @FXML private var SKINS_INDICATOR: Rectangle,
                 @FXML private var UPPAT_INDICATOR: Rectangle,
                 @FXML private var QUICK_OPTIONS: Button,
                 @FXML private var QUIKPLAYS: HBox,
                 @FXML private var PROFILE_SELECTOR: ComboBox[profile],
                 @FXML private var PANE_INSTALLATIONS: Pane,
                 @FXML private var SEARCH_PROFILE: TextField,
                 @FXML private var PROFILE_SEARCH_TEXT: Text,
                 @FXML private var ORDER_BY: ChoiceBox[String],
                 @FXML private var PROFILE_ORDERBY_TEXT: Text,
                 @FXML private var VERSIONS_STABLE: CheckBox,
                 @FXML private var VERSIONS_SNAPSHOTS: CheckBox,
                 @FXML private var VERSIONS_MODs: CheckBox,
                 @FXML private var PROFILE_VERSIONS_TEXT: Text,
                 @FXML private var PROFILE_NEW: Button,
                 @FXML private var USERNAME: Text,
                 @FXML private var PROFILE_GET: Pane,
                 @FXML private var PROFILE_ICON_GET: ComboBox[ImageView],
                 @FXML private var PROFILE_NAME_GET: TextField,
                 @FXML private var PROFILE_VERSION_GET: ComboBox[String],
                 @FXML private var PROFILE_GAMEDIR_GET: TextField,
                 @FXML private var PROFILE_GET_SAVE: Button,
                 @FXML private var PROFILE_GET_CANCEL: Button,
                 @FXML private var SC_PANE_PROFILE: ScrollPane,
                 @FXML private var DOWNLOAD_PANE: Pane,
                 @FXML private var DOWNLOAD_BAR: ProgressBar,
                 @FXML private var DOWNLOAD_INFO_BAR: Label,
                 @FXML private var CONSOLE_TEXT: Text,
                 @FXML private var CONSOLE_ICON: ImageView,
                 @FXML private var CONSOLE_BUTTON_PANE: Pane,
                 @FXML private var CONSOLE_PANE: Pane,
                 @FXML private var CONSOLE_TEXT_FIELD: TextArea,
                 @FXML private var QPT: Button,
                 @FXML private var SETTINGS_PANE: Pane,
                 @FXML private var SETTINGS_NAME_CHANGE: Button,
                 @FXML private var SETTINGS_NAME: TextField
                )
                {
                  val log: Log = new Log(Controller.this.getClass)
                  log.info("INIT THE INTERFACE")

                  val username: String = System.getProperty("user.name")
                  val Operative: OperativeSystem = OperativeSystem.thisOperativeSystem();
                  val FilesDirectory: Path = Paths.get(String.format(if (Operative == OperativeSystem.Linux) "/home/%s/lt/" else "C:/Users/%s/Documents/.LT",username))

                  log.info("load properties: /net/k3nder/launchertrinity/settings/settings.properties")
                  private val property: Properties = new Properties
                  property.load(new FileReader("C:\\Users\\krist\\Desktop\\untitled\\src\\main\\scala\\settings\\settings.properties"))

                  log.info("load configurations assets")
                  val config: ConfigFile = new ConfigFile(new File(FilesDirectory + "/assets/index.json"))
                  private val PROFILE_LIST: VBox = new VBox
                  log.info("create new Vbox: " + PROFILE_LIST.hashCode())

                  log.info("load console")
                  prepare_console();

                  log.info("create CPane: ")
                  private val GAME_PANE: CPane = new CPane(PANE_GAME,true)
                  log.data("GAME_PANE", GAME_PANE)
                  private val INSTALATIONS_PANE: CPane = new CPane(PANE_INSTALLATIONS,true)
                  log.data("INSTALATIONS_PANE",INSTALATIONS_PANE)
                  private val GET_PROFILE: CPane = new CPane(PROFILE_GET,true)
                  log.data("PROFILE_GET",GET_PROFILE)
                  private val PANE_CONSOLE: CPane = new CPane(CONSOLE_PANE,true)
                  log.data("PANE_CONSOLE", PANE_CONSOLE)
                  private val PANE_SETTINGS: CPane = new CPane(SETTINGS_PANE,true)
                  log.data("PANE_SETTINGS",PANE_SETTINGS)
                  private val PANE_MENU: CPane = new CPane(MENU_PANE,false)
                  log.data("PANE_MENU",PANE_MENU)
                  private val MENU_OPTIONS: CPane = new CPane(OPTIONS_MENU,false)
                  log.data("OPTIONS_MENU",MENU_OPTIONS)

                  log.info("setting default profile get pane data")
                  setDefaultProfileGET()

                  log.info("setting version number: " + property.getProperty("version"))
                  VERSION_NUMBER.setText(property.getProperty("version"))

                  log.info("prepare profiles")
                  prepare_profiles()
                  log.info("prepare profile buttons")
                  prepare_profile_buttons()
                  log.info("prepare play button")
                  prepare_play_button()
                  log.info("prepare play button events")
                  prepare_play_button_events()

                  log.info("load profiles")
                  read_profiles()

                  log.info("load icons: " + config.getValue("settings_button",classOf[String]))
                  SETTINGS_ICON.setImage(new Image("file:///" + FilesDirectory + "/assets/" + config.getValue("settings_button",classOf[String])))

                  PROFILE_GET_CANCEL.setOnAction(_ => {INSTALATIONS_PANE.open(true)})

                  log.info("load panorama image")
                  IMG_GAME.setPreserveRatio(false)
                  IMG_GAME.setFitHeight(600)
                  IMG_GAME.setImage(new Image("file:///" + FilesDirectory + "/assets/" + config.getValue("panorama_img",classOf[String])))

                  log.info("prepare settings button events")
                  prepare_settings_button_events()

                  log.info("prepare setting")
                  prepare_settings_save_change_buttons()

                  log.info("prepare console button events")
                  prepare_console_button_events()

                  log.info("prepare buttons effects")
                  set_entered_exited_prepareALL()

                  log.info("prepare indicators")
                  preparateIndicators()

                  log.info("[TEST] prepare QPTest")
                  QPT.setOnAction(_ => {
                    val thread = new Thread(new Runnable {
                      override def run(): Unit = {
                        //val mc = new Mc()
                      }
                    })
                  })

                  PROFILE_SELECTOR.setStyle("-fx-background-color: TRANSPARENT;")
                  log.info("DONE")
                  GAME_PANE.open(true)
                  val trinityConfig = new ConfigFile(new File(FilesDirectory + "\\trinity.settings.json"))
                  if(!trinityConfig.getMap.containsKey("username")) PANE_SETTINGS.open(true)

                  MENU_OPTIONS.Open(true)
                  PANE_MENU.Open(true)
                  GAME_PANE.Open(true)

                  private def set_entered_exited_prepareALL(): Unit = {
                    setEnteredExited(TEXT_SKINS)
                    setEnteredExited(TEXT_INSTALATIONS)
                    setEnteredExited(TEXT_PLAY)
                    setEnteredExited(TEXT_UPDATE_PATCH)
                  }
                  private def setEnteredExited(text: Text): Unit = {
                    text.setFill(Color.LIGHTGRAY)

                    text.setOnMouseEntered(_ =>{
                      text.setFill(Color.WHITE)
                    })

                    text.setOnMouseExited(_ =>{
                      text.setFill(Color.LIGHTGRAY)
                    })
                  }
                  private def preparateIndicators(): Unit = {
                    // creamos lo indicadores para indicar en uqe sitio estamos
                    val skins_i: Indicator = new Indicator(SKINS_INDICATOR)
                    val play_i: Indicator = new Indicator(PLAY_INDICATOR)
                    val instalations_i: Indicator = new Indicator(INSTALL_INDICATOR)
                    val updp_i: Indicator = new Indicator(UPPAT_INDICATOR)

                    // les añadimos acciones
                    skins_i.setOnAction(() => {
                      //log.info("open skins pane")
                    })
                    play_i.setOnAction(() => {
                      //log.info("open play pane")
                      GAME_PANE.open(true)
                    })
                    instalations_i.setOnAction(() => {
                      INSTALATIONS_PANE.open(true)
                    })
                    updp_i.setOnAction(() => {
                    })
                    // los vinculamos
                    Indicator.LinkIndicator(TEXT_SKINS, skins_i)
                    Indicator.LinkIndicator(TEXT_PLAY, play_i)
                    Indicator.LinkIndicator(TEXT_INSTALATIONS, instalations_i)
                    Indicator.LinkIndicator(TEXT_UPDATE_PATCH,updp_i)
                  }
                  private def generateProfileToRead(): Unit = {

                    val username: String = System.getProperty("user.name")
                    val Operative: OperativeSystem = OperativeSystem.thisOperativeSystem();
                    val FilesDirectory: Path = Paths.get(String.format(if (Operative == OperativeSystem.Linux) "/home/%s/lt/" else "C:/Users/%s/Documents/.LT",username))

                    val prof: profile = new profile();
                    prof.lastVersionId = PROFILE_VERSION_GET.getValue
                    prof.name = PROFILE_NAME_GET.getText
                    prof.gameDir = PROFILE_GAMEDIR_GET.getText
                    val Profiles: ConfigFile = new ConfigFile(new File(FilesDirectory + "\\trinity.profiles.json"))
                    val lis: util.List[profile] = Profiles.getArrayList("profiles",classOf[profile])
                    lis.add(prof)
                    Profiles.putValue("profiles",lis)
                    Profiles.save();
                    PROFILE_LIST.getChildren.removeAll()
                  }
                  private def setDefaultProfileGET(): Unit = {
                    val versions: ObservableBuffer[String] = ObservableBuffer()
                    val convertedScalaList: List[Vmc] = new mc.Manifest().getList.toArray(Array[Vmc]()).toList
                    log.info("founds versions: ")
                    for(version: Vmc <- convertedScalaList){
                      versions.add(version.getID)
                      log.data("found version: ", version)
                    }
                    PROFILE_VERSION_GET.setStyle("-fx-text-fill: WHITE;")
                    PROFILE_VERSION_GET.setItems(versions)
                    GET_PROFILE.setOnOpen(() => {
                      PROFILE_GAMEDIR_GET.setText(Mc.MINECRAFT_PATH)
                      PROFILE_NAME_GET.setText("undnamed")
                      PROFILE_VERSION_GET.setValue(new mc.Manifest().getLastVersion.getID)
                    })
                  }
                  @FXML
                  def PLAYA(): Unit = {
                    var version: Vmc = null;
                    val p = PROFILE_SELECTOR.getValue
                    if(p == null) {JOptionPane.showMessageDialog(null,"no selected profile"); return}

                    if(new net.kender.core.mc.Manifest().exist(p.lastVersionId)){
                      version = new net.kender.core.mc.Manifest().getVersion(p.lastVersionId)
                    } else {
                      version = new Vmc(p.lastVersionId,Vtype.custom,null,p.lastVersionId);
                    }
                    val tr: Thread = new Thread(new Runnable() {
                      override def run(): Unit = {
                        CONSOLE_TEXT_FIELD.setText("")
                        DOWNLOAD_PANE.setVisible(true)
                        DOWNLOAD_PANE.setDisable(false)
                        val mc: Mc = new Mc(Paths.get(Mc.MINECRAFT_PATH),version)
                        mc.setGameDir(Paths.get(if (p.gameDir == null ) Mc.MINECRAFT_PATH else p.gameDir))
                        mc.onFinaly(() => {
                          DOWNLOAD_PANE.setVisible(false)
                          DOWNLOAD_PANE.setDisable(true)
                        })
                        mc.setOnStep((step: Float,stepname: String) => {
                          DOWNLOAD_BAR.setProgress((step / 100)+0.1)
                          Platform.runLater(new Runnable() {
                            override def run(): Unit = {
                              DOWNLOAD_INFO_BAR.setText(stepname)
                              // Tu código de actualización de interfaz de usuario aquí
                            }
                          })

                        })
                        mc.run()
                      }
                    })
                    tr.start()
                  }
                  private def prepare_play_button_events(): Unit = {
                    PLAY_BUTTON.setBackground(new Background(new BackgroundImage(
                      new Image("file:///" + FilesDirectory + "/assets/" + config.getValue("play_button", classOf[String])),
                      BackgroundRepeat.NO_REPEAT,
                      BackgroundRepeat.NO_REPEAT,
                      BackgroundPosition.DEFAULT,
                      new BackgroundSize(240, 63, true, true, true, false)
                    )))
                    PLAY_BUTTON.setOnMouseEntered(_ => {
                      println("play drag")
                      PLAY_BUTTON.setBackground(new Background(new BackgroundImage(
                        new Image("file:///" + FilesDirectory + "/assets/" + config.getValue("play_button_pressed", classOf[String])),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(100, 100, true, true, true, false)
                      )))
                    })
                    PLAY_BUTTON.setOnMouseExited(_ => {
                      PLAY_BUTTON.setBackground(new Background(new BackgroundImage(
                        new Image("file:///" + FilesDirectory + "/assets/" + config.getValue("play_button", classOf[String])),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(100, 100, true, true, true, false)
                      )))
                    })
                  }
                  private def prepare_profiles(): Unit = {
                    val username: String = System.getProperty("user.name")
                    val Operative: OperativeSystem = OperativeSystem.thisOperativeSystem();
                    val FilesDirectory: Path = Paths.get(String.format(if (Operative == OperativeSystem.Linux) "/home/%s/lt/" else "C:/Users/%s/Documents/.LT",username))

                    SC_PANE_PROFILE.setFitToHeight(true)
                    PROFILE_LIST.setSpacing(10)
                    SC_PANE_PROFILE.setContent(PROFILE_LIST)
                    PROFILE_SELECTOR.setItems(new ObservableBuffer[profile])
                    PROFILE_SELECTOR.setOnAction(_ => {
                      if(PROFILE_SELECTOR.getValue != null){
                        val config = new ConfigFile(new File(FilesDirectory + "\\trinity.profiles.json"))
                        config.putValue("selected",PROFILE_SELECTOR.getValue)
                        config.save()
                      }
                    })
                  }
                  private def read_profiles(): Unit = {
                    val username: String = System.getProperty("user.name")
                    val Operative: OperativeSystem = OperativeSystem.thisOperativeSystem();
                    val FilesDirectory: Path = Paths.get(String.format(if (Operative == OperativeSystem.Linux) "/home/%s/lt/" else "C:/Users/%s/Documents/.LT",username))

                    val Profiles: ConfigFile = new ConfigFile(new File(FilesDirectory + "\\trinity.profiles.json"))
                    if(Profiles.getMap.containsKey("selected")) PROFILE_SELECTOR.setValue(Profiles.getValue("selected",classOf[profile]));
                    val prof: util.List[profile] = Profiles.getArrayList("profiles", classOf[profile])
                    val profi: ObservableBuffer[profile] = ObservableBuffer()
                    log.info("profiles: ")
                    PROFILE_LIST.getChildren.removeAll()
                    PROFILE_LIST.getChildren.clear()
                    for (p: profile <- prof.asScala) {
                      val pane: Pane = new ObservableProfile(p).getPane()
                      layout.HBox.setHgrow(pane, javafx.scene.layout.Priority.ALWAYS)
                      pane.setId(p.toJson())
                      profi.add(p)

                      PROFILE_LIST.getChildren.add(pane)
                      log.data("profile",p.toJson())
                    }
                    PROFILE_SELECTOR.getItems.clear()
                    PROFILE_SELECTOR.setItems(profi)
                  }
                  private def prepare_play_button(): Unit = {
                    val customFont: Font = Font.loadFont(getClass.getResourceAsStream("font/font.ttf"), 30)
                    PLAY_BUTTON.setFont(customFont)
                    PLAY_BUTTON.setTextFill(Color.WHITE)
                  }
                  private def prepare_settings_button_events(): Unit = {
                    SETTINGS_BUTTON_PANE.setOnMouseEntered(_ => {
                      println("drag")
                      SETTINGS_BUTTON_PANE.setStyle("-fx-background-color: #373537;")
                    })
                    SETTINGS_BUTTON_PANE.setOnMouseExited(_ => {
                      SETTINGS_BUTTON_PANE.setStyle("-fx-background-color: #1e1e1e;")
                    })
                    SETTINGS_BUTTON_PANE.setOnMouseClicked(_ => {
                      PANE_SETTINGS.open(true)
                    })
                  }
                  private def prepare_settings_save_change_buttons(): Unit = {
                    val trinityConfig = new ConfigFile(new File(FilesDirectory + "\\trinity.settings.json"))
                    SETTINGS_NAME.setText((if (trinityConfig.getMap.containsKey("username")) trinityConfig.getValue("username",classOf[String]) else USERNAME.getText))
                    SETTINGS_NAME_CHANGE.setOnAction(_ => {
                        GAME_PANE.open(true)
                        val trinityConfig = new ConfigFile(new File(FilesDirectory + "\\trinity.settings.json"))
                        trinityConfig.putValue("username",SETTINGS_NAME.getText)
                        trinityConfig.save()
                        USERNAME.setText(trinityConfig.getValue("username",classOf[String]))
                    })
                    USERNAME.setText(trinityConfig.getValue("username",classOf[String]))
                  }
                  private def prepare_console_button_events(): Unit = {
                    CONSOLE_BUTTON_PANE.setOnMouseEntered(_ => {
                      println("drag")
                      CONSOLE_BUTTON_PANE.setStyle("-fx-background-color: #373537;")
                    })
                    CONSOLE_BUTTON_PANE.setOnMouseExited(_ => {
                      CONSOLE_BUTTON_PANE.setStyle("-fx-background-color: #1e1e1e;")
                    })
                    CONSOLE_BUTTON_PANE.setOnMouseClicked(_ => {
                      PANE_CONSOLE.open(true)
                    })
                  }
                  private def prepare_profile_buttons(): Unit = {
                    PROFILE_NEW.setOnAction(_ => {
                      GET_PROFILE.open(true)
                    })
                    PROFILE_GET_SAVE.setOnAction(_ => {
                      generateProfileToRead()
                      read_profiles()
                      INSTALATIONS_PANE.open(true)
                    })
                  }
                  private def prepare_console(): Unit = {
                    CONSOLE_TEXT_FIELD.setEditable(false)
                    CONSOLE_TEXT_FIELD.setScrollLeft(Double.MaxValue)
                    CONSOLE_ICON.setImage(new Image("file:///" + FilesDirectory + "/assets/" + config.getValue("command_button",classOf[String])))
                    EXTRAS.setPrintStream(new PrintStream(new CustomOutputStream(CONSOLE_TEXT_FIELD)))
                  }
                  private def prepare_animation(): Unit = {

                  }
                }


class CustomOutputStream(private var textArea: TextArea) extends OutputStream {
  @throws[IOException]
  def write(b: Int): Unit = {
    // Se debe actualizar la interfaz de usuario de JavaFX en el hilo de la interfaz de usuario de JavaFX
    Platform.runLater(() => {

      // agrega el carácter al final del TextArea
      textArea.appendText(String.valueOf(b.toChar))
      // desplaza el TextArea a la última posición para que el último texto agregado sea visible
      textArea.setScrollTop(java.lang.Double.MAX_VALUE)

    })
  }
}