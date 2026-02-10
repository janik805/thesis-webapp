# Dokumentation - Thesis is coming!
## Einführung and Ziele
### Was ist Thesis?
- Thesis ist eine Plattform, mit der man ein Thema und eine Betreuer:in für seine Bachelorarbeit finden kann

### Wesentliche Features
- Filter-Funktion für Betreuende und Themen
- Matching-Funktion um Betreuende und Themen nach individuellen Interessen und Modulen zu sortieren
- Erstellungs-Funktion für Profile von Betreuenden mit Themen Erstellung und Datei-Upload

### Qualitätsziele
- Wartbarkeit
- Accessibility
- Einfache Bedienung

### Stakeholder
- Entwickler
- Betreuende
- Studenten
- Jens Bendisposto

## Randbedingungen

### Technische Randbedingungen
- Implementierung in Java
- Deployment mit Docker

### Organisatorische Randbedingungen
- Studentengruppe von 3 Studenten, die das Projekt parallel zu dem Modul Programmierpraktikum 2 entwickelt haben
- Das Projekt wurde in von November 2025 bis Januar 2026 entwickelt

### Konventionen
- Die Architekturdokumentation wurde nach dem arc42 Template erstellt
- Die Software wurde mit Checkstyle nach dem Google Standard geprüft und mit SpotBugs auf Fehler untersucht
- Sprache der WebApp ist Deutsch; Commits wurden in Englisch verfasst
- Tests folgen der AAA-Struktur

## Kontextabgrenzungen

### Fremdsysteme
- Github OAuth2 Login als Authentication token zum Anmelden

## Lösungsstrategie
<table>
    <thead>
        <tr>
            <th>Qualitätsziel</th>
            <th>Szenario</th>
            <th>Lösungsansatz</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>Einheitliche Architektur</td>
            <td>Onion-Architektur für Wartbarkeit und Testbarkeit</td>
            <td>Dateien wurden in die passenden Schichten mit korrekten Referenzen eingeordnet.
                Dient zur Isolierung von Geschäftslogik von der Infrastruktur und Persistenzebene.
                Die Onion-Architektur wird zudem durch Architekturtests auf Korrektheit überprüft.</td>
        </tr>
        <tr>
            <td>Sicherheit</td>
            <td>Authentifizierung und Autorisierung durch OAuth2, SpotBugs</td>
            <td>Identitätsprüfung durch die Github-Id mit OAuth2. Zugriffskontrolle durch @Secured in Klassen- und Methodenebene. 
                Zusätzliche nötige Id-Prüfung bei Themen. Es wurde auf potenzielle Sicherheitslücken mit SpotBugs gesucht.</td>
        </tr>
        <tr>
            <td>Datenbank-Integration</td>
            <td>Strukturierte Speicherung von Daten</td>
            <td>Anbindung der Datenbank durch Spring JDBC mit CrudRepository in der Persistenzschicht.
                Verwendet relationale Datenbank PostgreSQL, welche über einem Docker Container bereitgestellt wurde.</td>
        </tr>
        <tr>
            <td>Nebenläufigkeit</td>
            <td>Konsistente Bearbeitung und Betrachtung von Daten in der Datenbnk</td>
            <td>Optimistisches Locking durch zusätzliches Attribut version mit @version Annotation im DTO in der Persistenzschicht.
                </td>
        </tr>
    </tbody>
</table>

## Bausteinsicht
### Level-1 Bausteinsicht
```text
+-----------------------+
|     Github-OAuth2     |
|   (externes System)   |
+-----------------------+
            ^
            |
+-----------------------+
| Security-Configuration|
| (UserService, Config) |
+-----------------------+
            
+-----------------------+            +-----------------------+
|      Web-Schicht      |    --->    |  Application-Schicht  |
|     (Controller)      |            | (Services, Aggregate) |
+-----------------------+            +-----------------------+
                                                ^
                                                |
                                     +-----------------------+
                                     |  Persistence-Schicht  |
                                     |   (Repositories, DB)  |
                                     +-----------------------+
```
<table>
    <thead>
        <tr>
            <th>Subsystem</th>
            <th>Kurzbeschreibung</th>
        </tr>
    </thead> 
    <tbody>
        <tr>
            <td>Security-Configuration</td>
            <td>Dient der Autorisierung und Authentifizierung über Github</td>
        </tr>
        <tr>
            <td>Github OAuth2</td>
            <td>externer Service für die Authentifizierung</td>
        </tr>
        <tr>
            <td>Web-Schicht</td>
            <td>Empfängt get und post Requests; zuständig für Templates</td>
        </tr>
        <tr>
            <td>Application-Schicht</td>
            <td>Geschäftslogik; enthält für Services und Aggregate</td>
        </tr>
        <tr>
            <td>Persistence-Schicht</td>
            <td>technische Datenspeicherung; zuständig für Speicherung und Laden aus der Datenbank</td>
        </tr>
    </tbody>
</table>

## Laufzeitsicht
### Szenario: Login
Beteiligte Bausteine:
- Web-Schicht
- Security-Configuration
- Github OAuth2
- Application-Schicht
- Persistence-Schicht

Prozess:
1. User geht auf eine geschützte Seite
2. Security-Configuration fängt die Anfrage ab
3. Security-Configuration leitet den Nutzer weiter zu Authentifizierung mit Github OAuth2
4. Security-Configuration reichert das Authentication-Token mit Rechten an
   1. Security-Configuration gibt Application-Schicht User Id um zu überprüfen ob der Nutzer ein/e Betreuer:in ist
   2. Application-Schicht lädt die Profile aus der Persistenz-Schicht
## Verteilungssicht
 - Anwendung wird mit Gradle bootJar gebaut und mit Docker Compose gestartet
## Querschnittliche Konzepte 
## Entscheidungen
<table>
    <thead>
        <tr>
            <th>Abschnitt</th>
            <th>Beschreibung</th>
        </tr>
    </thead> 
    <tbody>
        <tr>
            <td>Titel</td>
            <td>Konstruktor-Injection mit Spring</td>
        </tr>
        <tr>
            <td>Kontext</td>
            <td>SpotBugs weist auf potenziellen Problemen bei Injection von veränderbaren Objekten im Konstruktor</td>
        </tr>
        <tr>
            <td>Entscheidung</td>
            <td>Warnungen werden mit @SuppressFBWarnings ignoriert, da die Objekte nie nach außen weitergegeben werden</td>
        </tr>
        <tr>
            <td>Status</td>
            <td>Akzeptiert</td>
        </tr>
        <tr>
            <td>Konsequenzen</td>
            <td>Keine funktionalen Auswirkungen. Instanziierung erfolgt durch Spring,
                erwähnte Felder sind private und/oder haben keine externen Zugriffsmöglichkeiten.</td>
        </tr>
    </tbody>
</table>

## Qualitätsanforderungen
## Risiken
<table>
    <thead>
        <tr>
            <th>Risiko</th>
            <th>Gefahr</th>
            <th>Maßnahmen</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>Datei-Upload von Malware</td>
            <td>hoch</td>
            <td>Skript tags werden bei Markdown-Dateien gefiltert und sind nicht erlaubt</td>
        </tr>
        <tr>
            <td>CSRF-Angriff über Formulare</td>
            <td>hoch</td>
            <td>Durch Spring Security und das Thymeleaf action Attribut das Programm vor dem Angriff gesichert</td>
        </tr>
        <tr>
            <td>XSS-Angriff </td>
            <td>hoch</td>
            <td>Durch das Thymeleaf text Attribut ist das Programm vor dem Angriff gesichert</td>
        </tr>
        <tr>
            <td>Github OAuth2 Ausfall</td>
            <td>sehr niedrig</td>
            <td>Login nicht möglich. Dadurch entsteht aber auch keine Sicherheitslücke</td>
        </tr>
    </tbody>
</table>

## Glossar
<table>
    <thead>
        <tr>
            <th>Begriff</th>
            <th>Definition</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>Profil</td>
            <td>Profil eines Betreuenden </td>
        </tr>
        <tr>
            <td>Betreuende</td>
            <td>Personen, die Abschlussarbeiten betreuen</td>
        </tr>
        <tr>
            <td>Thema</td>
            <td>Mögliches Thema einer Abschlussarbeit eines Betreuenden</td>
        </tr>
        <tr>
            <td>Voraussetzung</td>
            <td>Voraussetzung der Abschlussarbeit</td>
        </tr>
        <tr>
            <td>Fachgebiete / Interessen</td>
            <td>Tags, die Fachgebiete von Betreuenden und Themen angeben und von der Filter-Funktion sowie von der Matching-Funktion verwendet werden</td>
        </tr>
    </tbody>
</table>