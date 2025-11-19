@echo off
setlocal ENABLEDELAYEDEXPANSION

set API_URL=https://central.sonatype.com/api/v1/publisher/upload
set USERNAME=%SONATYPE_USERNAME%
set PASSWORD=%SONATYPE_PASSWORD%

set GROUP_ID=io.github.denis535
set ARTIFACT_ID=state-machine-pro
set VERSION=1.0.0

set FILES_PATH=distribution\io\github\denis535\state-machine-pro\%VERSION%
set REPO_PATH=%GROUP_ID:.=/%/%ARTIFACT_ID%/%VERSION%

if "%USERNAME%"=="" (
    echo ERROR: SONATYPE_USERNAME is not set
    pause
    exit /b 1
)

if "%PASSWORD%"=="" (
    echo ERROR: SONATYPE_PASSWORD is not set
    pause
    exit /b 1
)

echo Files path: %FILES_PATH%
echo Repository path: %REPO_PATH%

if not exist "%FILES_PATH%" (
    echo ERROR: Directory does not exist: %FILES_PATH%
    pause
    exit /b 1
)

if exist "%FILES_PATH%\%ARTIFACT_ID%-%VERSION%.pom" (
    echo Uploading POM...
    curl -s -o NUL -w "HTTP %%{http_code}\n" ^
        -u "%USERNAME%:%PASSWORD%" ^
        -X POST ^
        -F "bundle=@%FILES_PATH%\%ARTIFACT_ID%-%VERSION%.pom;type=application/octet-stream" ^
        "%API_URL%?path=%REPO_PATH%"
) else (
    echo ERROR: Missing POM file!
    pause
    exit /b 1
)

set FOUND=0
for %%F in ("%FILES_PATH%\*") do (
    set FILE_NAME=%%~nxF
    if /I not "%%~xF"==".pom" if /I not "%%~xF"==".md5" if /I not "%%~xF"==".sha1" if /I not "%%~xF"==".sha256" if /I not "%%~xF"==".sha512" if /I not "%%~xF"==".asc" (
        set FOUND=1
        echo Uploading: !FILE_NAME!
        curl -s -o NUL -w "HTTP %%{http_code}\n" ^
            -u "%USERNAME%:%PASSWORD%" ^
            -X POST ^
            -F "bundle=@%%F;type=application/octet-stream" ^
            "%API_URL%?path=%REPO_PATH%"
    )
)

if "!FOUND!"=="0" (
    echo WARNING: No additional artifacts found to upload.
)

echo Publish completed.
pause
