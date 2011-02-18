<<<<<<< HEAD
# Add your own tasks in files placed in lib/tasks ending in .rake,
# for example lib/tasks/capistrano.rake, and they will automatically be available to Rake.

require(File.join(File.dirname(__FILE__), 'config', 'boot'))

require 'rake'
require 'rake/testtask'
require 'rake/rdoctask'

require 'tasks/rails'
=======
require 'rake'
require 'rake/testtask'

desc 'Default: run acts_as_list unit tests.'
task :default => :test

desc 'Test the acts_as_ordered_tree plugin.'
Rake::TestTask.new(:test) do |t|
  t.libs << 'lib'
  t.pattern = 'test/**/*_test.rb'
  t.verbose = true
end
>>>>>>> 7fd4e41bb0ae02bc138f2553f3d5f3598aebbe9d
