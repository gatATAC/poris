# Docker Rails 2.3.5

Use this to develop legacy Rails 2.3 and Ruby 1.8.7 apps without having to build a legacy environment on your computer. Also use this to start an upgrade path to a more recent version of Ruby and Rails.

## A warning

I built this out of a desire to play around with Docker and see if it was appropriate for local app development. Things may not be set up well and some of my instructions might be wrong. Pull requests and issues are very welcome!

## Getting up and running

Copy all of the files in this repo into your Rails project. Run `docker-compose up`. If you get gem install errors it's extremely likely that all the gems you need aren't enumerated in the app's `config/environment.rb` file. Run `gem list` on the production server and copy the gems and their versions into `config/environment.rb` and then run `docker-compose build rails` or `docker-compose up --build`

Connect to the container with `docker-compose run --rm rails bash`, which will give you enough of a shell environment to run basic rake commands. Once you're shelled in just bootstrap the way you would any other Rails app (`rake db:create`, `rake db:schema:load`, etc).

Don't run `script/server` from bash, that happens automatically when you run `docker-compose up`.

## Deploying and running in production

While launching Docker containers into production is probably a great idea, it's beyond the scope of this project for now.
