@ECHO OFF

SET "ORI=D:\sk_shieldus\hacking02_sk\sk_sesac_pt2\hacking02_sk"
FOR /F %%P IN ('cd') DO SET PATH=%%P
IF "%ORI%" == "%PATH%" (
	
	ECHO SUCCESS
)
