# Chess

Ein modernes Schach-Projekt in Java mit geplanter Multiplayer-Funktionalität.

## 🧩 Installation & Ausführung

---

1. **Repository klonen**
    ```bash
    git clone https://github.com/kaiserol/Chess.git
    cd 4D_Viewer
    ```

2. **Abhängigkeiten installieren & Build erzeugen**
    ```bash
    mvn clean install && rm target/original-*.jar
    ```

3. **Anwendung starten**

   _Option A – Über die erzeugte JAR-Datei_
    ```bash
    java -jar target/*.jar
    ```

   _Option B – Direkt über Maven_
    ```bash
    mvn exec:java
    ```

## 🛠️ Entwicklung

---

### Technologie-Stack

- **IDE:** IntelliJ IDEA (Community / Ultimate)
- **Sprache**: Java
- **Build-Tool**: Maven
- **Testen**: JUnit 5
- **Architektur**: Objektorientiertes Design mit klarer Trennung von Spielelogik und Darstellung

### Wichtige Maven-Befehle

| Befehl                   | Zweck                            |
|--------------------------|----------------------------------|
| `mvn clean install`      | Projekt bereinigen und neu bauen |
| `mvn compile`            | Kompilieren des Projekts         |
| `mvn dependency:resolve` | Nur Abhängigkeiten aktualisieren |
| `mvn test`               | Tests ausführen                  |

## ⚙️ Troubleshooting

---

### Java-Version prüfen

- Prüfen Sie, ob Java 24 installiert ist:
    ```bash
    java -version
    ```
- Beispielsausgabe:
    ```bash
    openjdk version "24.0.1" 2025-04-15
    ```

Falls eine andere Version angezeigt wird, ggf. `JAVA_HOME` anpassen oder eine passende JDK-Version installieren.

### Hinweise für IntelliJ IDEA

1. Projekt öffnen → `4D_Viewer/`
2. Project SDK auswählen: **JDK 24**
3. IntelliJ erkennt automatisch das Maven-Projekt
4. Zum Starten:
    - Menü: *Run → Run 'Main'*
    - oder eigene Run-Konfiguration mit Main-Class (z. B. `de.uzk.Main`)

## 📋 Projektbeschreibung

---

**Chess** ist eine Schach-Implementierung in Java. Das Projekt
befindet sich in aktiver Entwicklung und wird um neue Features erweitert.

### 🎯 Aktuelle Features

- Vollständige Implementierung aller Schachfiguren (König, Dame, Turm, Läufer, Springer, Bauer)
- Regelkonforme Zugvalidierung für alle Figuren
- 8x8 Schachbrett mit Feldverwaltung
- Konsolenbasierte Darstellung des Spielbretts
- Umfangreiche Unit-Tests für Spiellogik

### 🚀 Geplante Features

- **Multiplayer-Server**: Online-Schach gegen andere Spieler
- Grafische Benutzeroberfläche (GUI)
- Spielstandspeicherung und -wiederherstellung
- Schach-Notation (PGN-Format)
- KI-Gegner mit verschiedenen Schwierigkeitsgraden