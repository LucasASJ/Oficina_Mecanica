@echo off
REM Compilar o projeto
javac -cp "lib\gson-2.8.9.jar" -d bin src\com\oficinamecanica\main\Main.java

IF %ERRORLEVEL% NEQ 0 (
    echo Erro na compilação. Verifique se o gson-2.8.9.jar está na pasta lib e se os arquivos .java estão corretos.
    pause
    exit /b
)

REM Executar o projeto
java -cp "bin;lib\gson-2.8.9.jar" com.oficinamecanica.main.Main

pause
