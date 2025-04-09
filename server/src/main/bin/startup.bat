@echo off
set "mimiFileView_BIN_FOLDER=%cd%"
cd "%mimiFileView_BIN_FOLDER%"
echo Using mimiFileView_BIN_FOLDER %mimiFileView_BIN_FOLDER%
echo Starting mimiFileView...
echo Please check log file in ../log/mimiFileView.log for more information
echo You can get help in our official home site: https://kkview.cn
echo If you need further help, please join our kk opensource community: https://t.zsxq.com/09ZHSXbsQ
echo If this project is helpful to you, please star it on https://gitee.com/kekingcn/file-online-preview/stargazers
java -Dspring.config.location=..\config\application.properties -jar mimiFileView-4.4.0.jar -> ..\log\mimiFileView.log
