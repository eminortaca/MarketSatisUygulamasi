@echo off
echo Derleniyor...
if not exist bin mkdir bin
javac -encoding UTF-8 -d bin src/model/*.java src/service/*.java src/ui/*.java
if %errorlevel% equ 0 (
    echo Derleme basarili!
    echo Uygulama baslatiliyor...
    java -cp bin ui.MarketGUI
) else (
    echo Derleme hatasi!
    pause
)
