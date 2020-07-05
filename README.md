<b>Information</b><br>
This project is a web application, that has been being developed using Spring Boot technology.

<b>Task</b><br>
Система "Что? Где? Когда?". Система состоит из Знатоков и Судьи. Знатоки отвечают на Вопросы разного типа (с варинтами ответа и без). Судья принимает ответы от Знатоков, опередляет правильность ответа, и защитывает очко команде знатоков (в случае правильного ответа) или команде противников (если Время вышло, или ответ не правильный). Судья также может при запросе от знатоков давать Подсказки разного типа. Сервисы: Время - засекает время между началом и концом вопроса в соответствии с Конфигурацией. Подсказки: • может выводить вероятность правильного ответа (при выборе из нескольких вариантов),  • либо убрать несколько неправильных вариантов (при выборе из нескольких вариантов),  • либо дать текстовую подсказку (заранее прикреплена к вопросу) • либо дать дополнительное время, и т.п. Статистика - отображает статистику после окончания игры. Формат определяется Конфигурацией Конфигурация - управляет настройками системы: время, количество игроков, тип подсказок, количество вопросов, и т.п. 


<b>Installing instruction</b><br>
You need to download project by click Clone or download project.

You can download zip or clone project , just write in git bush console git clone 
https://github.com/nikitabortnichuk/WhatWhereWhen_Spring

<b>Launching instruction</b><br>
To run this app you should have installed Java and Maven on your local machine.

Launch "game_www_spring.sql" from resources/database at your sql server At src/main/resources/application.properties change properties for your sql server Launch main method in Main.java class Open your browser and in the url write http://localhost:9090/game-www/
