FROM cbiitssrepo/tomcat9
MAINTAINER icdc devops team

# add the war file
ARG WAR_FILE

ADD target/${WAR_FILE} /usr/local/tomcat/webapps/ROOT.war