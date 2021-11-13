FROM ubuntu:16.04

# Install packages for building ruby
RUN apt-get update
RUN apt-get install -y --force-yes build-essential curl git sqlite3 libsqlite3-dev nano
RUN apt-get install -y --force-yes zlib1g-dev libssl-dev libreadline-dev libyaml-dev libxml2-dev libxslt-dev mysql-client libmysqlclient-dev
RUN apt-get clean

# Install rbenv and ruby-build
RUN git clone https://github.com/sstephenson/rbenv.git /root/.rbenv
RUN git clone https://github.com/sstephenson/ruby-build.git /root/.rbenv/plugins/ruby-build
RUN /root/.rbenv/plugins/ruby-build/install.sh
ENV PATH /root/.rbenv/bin:/root/.rbenv/shims:$PATH
RUN echo 'eval "$(rbenv init -)"' >> /etc/profile.d/rbenv.sh
RUN echo 'eval "$(rbenv init -)"' >> .bashrc
RUN eval "$(rbenv init -)"


# Install ruby and rubygems
RUN rbenv install 1.8.7-p374
RUN rbenv global 1.8.7-p374
RUN echo 'gem: --no-rdoc --no-ri' > /root/.gemrc
RUN gem install rubygems-update -v 1.3.6
RUN rbenv rehash
RUN update_rubygems --version=1.3.6
RUN rbenv rehash
RUN gem install rake -V -v 0.8.7
RUN rbenv rehash
RUN gem install bundler -v 1.8.7
RUN gem install sqlite3 -v 1.3.11
RUN gem install hobo -V -v 1.0.3
RUN gem install passenger
RUN rbenv rehash

WORKDIR /app
ENTRYPOINT ["./docker-entrypoint-poris.sh"]
