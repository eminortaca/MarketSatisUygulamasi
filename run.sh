#!/bin/bash

echo "Derleniyor..."
javac -encoding UTF-8 -d bin src/model/*.java src/service/*.java src/ui/*.java

if [ $? -eq 0 ]; then
    echo "Derleme basarili!"
    echo "Uygulama baslatiliyor..."
    java -cp bin ui.MarketGUI
else
    echo "Derleme hatasi!"
    read -p "Devam etmek icin bir tusa basin..."
fi
