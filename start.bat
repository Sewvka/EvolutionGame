@ECHO OFF 
TITLE Best team
ECHO Please wait, I will run n nodes nodes
SET "var="&for /f "delims=0123456789" %%i in ("%1") do set var=%%i
if defined var (echo %1 NOT numeric; exit) else (echo %1 numeric)
minikube start --nodes %1 -p node