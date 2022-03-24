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


#------------ This is a hack to solve the lack of taglibs/auto contents
# We will start the server in port 3001 using webrick, then we will kill the server once 
# it is working, so passenger already finds the taglibs/auto contents
echo "STARTING redmine for first time to ensure non-cosmosys plugins Redmine settings are created"
bundle exec script/server -p 3001 -e production &
echo "Waiting Redmine to start on localhost:3001..."
while ! nc -z localhost 3001; do   
  echo "    Redmine still not available on localhost:3001"
  sleep 3 # wait to check again
done
sleep 5 # just an extra wait just in case.

# Force a Http request to redmine as it makes additionals to create the entrance on "settings table"
curl http://localhost:3001;

echo "Redmine started. Stopping to finish plugins intalation."
if [ -f tmp/pids/server.pid ]; then
  kill -9 $(lsof -i tcp:3001 -t)
fi
echo "Server Stoped. Continuing installation."
#----------  End hack

passenger start -e production

echo "---- Waiting forever so anyone can open a session -----"
while :; do :; done & kill -STOP $! && wait $!

