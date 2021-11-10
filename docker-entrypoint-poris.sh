#!/bin/bash
################################################################################################
# 1.- 
# 2.- Configure poris if not configured previously
# 3.- Start poris server
################################################################################################
set -e
	
#rake db:migrate
#RAILS_ENV=production rake db:migrate

echo "---- Waiting forever so anyone can open a session -----"
while :; do :; done & kill -STOP $! && wait $!

