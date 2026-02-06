# Dokumentation - Thesis is coming!
## Einführung and Ziele
### Was ist Thesis?
- Thesis ist eine Plattform um ein Thema und eine Betreuer:in für seine Bachelorarbeit zu finden

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
## Bausteinsicht
## Laufzeitsicht
## Verteilungssicht
## Querschnittliche Konzepte 
## Entscheidungen
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