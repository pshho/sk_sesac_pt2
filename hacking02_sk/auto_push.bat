@ECHO OFF

SET "ORI=D:\sk_shieldus\hacking02_sk\sk_sesac_pt2\hacking02_sk"
FOR /F %%P IN ('cd') DO SET CURRENT_PATH=%%P
IF "%ORI%" == "%CURRENT_PATH%" (
	FOR /F "tokens=*" %%G IN ('gradlew build') DO SET MY_GRBUILD=%%G
	ECHO "%MY_GRBUILD%"
	
	:::FOR /F %%R IN ('(dir /B /A:-D) ^| findstr /I "ROOT.war"') DO SET MY_LIST=%%R
	:::ECHO %MY_LIST%
)