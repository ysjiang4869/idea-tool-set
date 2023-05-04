# IDEA-TOOL

![Build](https://github.com/ysjiang4869/IDEA-TOOL/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/18735.svg)](https://plugins.jetbrains.com/plugin/18735)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/18735.svg)](https://plugins.jetbrains.com/plugin/18735)

A tool for daily develop use!

## Plugin Description
<!-- Plugin description -->

This plugin is build for normal daily develop use. Include many useful tools every engineer need.
You can handle json, convert time, debug cron, generate random json from class and so on.

Features:
- Json tool: tree view, compact, and extract.
- Cron tool: write cron and valid, see the run times.
- Time tool: get current timestamp, convert between ts and date, with or without timezones.
- Date calculator: calc how many days between two date, add or sub days.
- Regex tool: write and valid regex.

Roadmap:
- [ ] json tool
  - [x] simplify and pretty
  - [x] treeView
  - [ ] to Java object
  - [ ] beautify the json view
  - [ ] add json search
- [x] date tool
  - [x] parse timestamp
- [x] cron tool
- [ ] Java object to random value json
- [x] regex tool
- [ ] encode, decode
- [ ] conversion of number systems
- [ ] curl to java code and vice versa
- [ ] jdk deserialize: technic research seems can't realize
- [ ] i18n support

... , if you have any idea or need, please open pull request at [github](https://github.com/ysjiang4869/idea-tool-set/pulls).
No more need to open your browser to use tool again!

<!-- Plugin description end -->

## Installation

- Using IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "IDEA-TOOL"</kbd> >
  <kbd>Install Plugin</kbd>

- Manually:

  Download the [latest release](https://github.com/ysjiang4869/IDEA-TOOL/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template

Thanks for [Jetbrains](https://www.jetbrains.com/community/opensource/#support) provide the excellent develop tool.
![JetBrains Logo (Main) logo](https://resources.jetbrains.com/storage/products/company/brand/logos/jb_beam.svg)

