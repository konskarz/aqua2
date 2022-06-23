@echo off
setlocal

set "_pf=%PROGRAMFILES%"
if "%PROCESSOR_ARCHITECTURE%"=="AMD64" set "_pf=%ProgramFiles(x86)%"

set "_trg=%_pf%\Java\jre6\bin\java.exe"
if exist "%_trg%" goto :_javainst

set "_src=%temp%\jre6.exe"
if exist "%_src%" goto :_javadl

call :_download "%_src%" "http://javadl.sun.com/webapps/download/AutoDL?BundleId=76208" "%0"
if not exist "%_src%" call :_error "%_src% not found"
:_javadl

"%_src%" /s ADDLOCAL=ALL IEXPLORER=1 MOZILLA=1 REBOOT=Suppress JAVAUPDATE=0
if not exist "%_trg%" call :_error "%_trg% not found"
:_javainst

set "_trg=%_pf%\MySQL\MySQL Server 5.0\bin\mysqld-nt.exe"
if exist "%_trg%" goto :_mysqlinst

set "_src=%temp%\mysql50.msi"
if exist "%_src%" goto :_mysqldl

call :_download "%_src%" "http://downloads.mysql.com/archives/get/file/mysql-essential-5.0.91-win32.msi" "%0"
if not exist "%_src%" call :_error "%_src% not found"
:_mysqldl

msiexec /i "%_src%" /quiet /norestart
if not exist "%_trg%" call :_error "%_trg% not found"
:_mysqlinst

pause
goto :eof

:_error
echo %~1
pause
exit 1

:_download
if not exist "%~dp1" md "%~dp1"
set "_vbs=%TEMP%\getbin.vbs"
>> "%_vbs%" (findstr "'--getbin.vbs" "%~3" | findstr /v "findstr")
cscript //nologo "%_vbs%" "%~2" "%~1"
del /q "%_vbs%"
goto :eof

:_unzip
if not exist "%~2" md "%~2"
set "_vbs=%TEMP%\unzip.vbs"
>> "%_vbs%" (findstr "'--unzip.vbs" "%~3" | findstr /v "findstr")
cscript //nologo "%_vbs%" "%~1" "%~2"
del /q "%_vbs%"
goto :eof

With CreateObject("WinHttp.WinHttpRequest.5.1") '--getbin.vbs
	.Open "GET", Wscript.Arguments(0), false '--getbin.vbs
	.setRequestHeader "User-Agent", WScript.ScriptName '--getbin.vbs
	.Send '--getbin.vbs
	WScript.Echo .getAllResponseHeaders '--getbin.vbs
	If .Status = 200 Then '--getbin.vbs
		ResponseBody = .ResponseBody '--getbin.vbs
		With CreateObject("ADODB.Stream") '--getbin.vbs
			.Open '--getbin.vbs
			.Type = 1 '//binary '--getbin.vbs
			.Write ResponseBody '--getbin.vbs
			.Position = 0 '--getbin.vbs
			.SaveToFile Wscript.Arguments(1), 2 '//overwrite '--getbin.vbs
		End With '--getbin.vbs
	End If '--getbin.vbs
End With '--getbin.vbs

With CreateObject("Shell.Application") '--unzip.vbs
	.NameSpace(Wscript.Arguments(1)).CopyHere .NameSpace(Wscript.Arguments(0)).Items '--unzip.vbs
End With '--unzip.vbs
