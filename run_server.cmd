@REM CLEAR THE SCREEN
CLS

@REM COMPILE THE FILES
javac -sourcepath src -d bin src/*java

@REM RUN THE SERVER
java -cp bin Server

@REM DON'T SUDDENLY QUIT
PAUSE
