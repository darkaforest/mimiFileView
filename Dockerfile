FROM darkaforest/mimifileview-base:4.4.0
ADD server/target/mimiFileView-*.tar.gz /opt/
ENV mimiFileView_BIN_FOLDER=/opt/mimiFileView-4.4.0/bin
ENTRYPOINT ["java","-Dfile.encoding=UTF-8","-Dspring.config.location=/opt/mimiFileView-4.4.0/config/application.properties","-jar","/opt/mimiFileView-4.4.0/bin/mimiFileView-4.4.0.jar"]
