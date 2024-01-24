@ECHO OFF

SET "ORI=D:\sk_shieldus\hacking02_sk\sk_sesac_pt2\hacking02_sk"
FOR /F %%P IN ('cd') DO SET CURRENT_PATH=%%P
IF "%ORI%" == "%CURRENT_PATH%" (
	ECHO PATH SAME
	SET "MY_BUILD="
	FOR /F "tokens=*" %%G IN ('gradlew build ^| find /I "BUILD SUCCESSFUL"') DO SET "MY_BUILD=%%G"
	
	IF %ERRORLEVEL% EQU 0 (
		ECHO BUILD SUCCESS
		call COPY build\libs\hacking02_sk-0.0.1-SNAPSHOT-plain.war "%CD%\ROOT.war"

		FOR /F "tokens=*" %%C IN ('git branch ^| find /I "* psh240124"') DO SET "MY_GIT=%%C"
		
		IF %ERRORLEVEL% EQU 0 (
			call git add .
			call git commit -m "update"
			call git pull origin master
			call git merge psh240124 master
			call git checkout master
			call git push origin psh240124
			call git checkout psh240124
			ECHO SUCCESS
		)
	)
)