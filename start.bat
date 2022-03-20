@ECHO OFF 
TITLE Best team
ECHO Please wait, I will run n nodes nodes
@REM if %1% == --help (ECHO "args:\n n - count of noudes to create")
minikube start --nodes %1 -p node