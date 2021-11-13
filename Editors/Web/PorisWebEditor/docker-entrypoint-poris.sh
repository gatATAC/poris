#!/bin/bash
################################################################################################
# 1.- 
# 2.- Configure poris if not configured previously
# 3.- Start poris server
################################################################################################
set -e

bundle install
rbenv rehash
rake db:migrate
#rake assets:precompile
RAILS_ENV=production rake db:migrate
#RAILS_ENV=production rake assets:precompile
passenger start -e production

echo "---- Waiting forever so anyone can open a session -----"
while :; do :; done & kill -STOP $! && wait $!

