FROM ubuntu:20.04
ARG DEBIAN_FRONTEND=noninteractive

RUN echo 'start build'
RUN LC_ALL=C.UTF-8

ARG DEBIAN_FRONTEND=noninteractive
RUN apt update
RUN apt upgrade -y
RUN echo 'start build'
RUN LC_ALL=C.UTF-8

RUN apt install -y php7.4 php7.4-dev php7.4-xdebug php7.4-redis gcc g++ git curl openjdk-8-jre-headless python3 python3-pip pipenv maven iputils-ping nano
RUN curl -sL https://deb.nodesource.com/setup_16.x | bash -
RUN apt update
RUN apt install -y nodejs
RUN php -r "copy('https://getcomposer.org/installer', 'composer-setup.php');"
RUN php composer-setup.php --install-dir=/usr/bin/ && rm composer-setup.php 

RUN git config --global http.proxyAuthMethod 'basic'
RUN git config --global http.sslverify false
RUN git config --global https.sslverify false

RUN echo '[XDebug]' >> /etc/php/7.4/cli/php.ini && echo "xdebug.remote_enable = 1" >> /etc/php/7.4/cli/php.ini && echo "xdebug.remote_autostart = 1" >> /etc/php/7.4/cli/php.ini

ENTRYPOINT tail -f /dev/null 
