class NodesEdgesController < ApplicationController

  hobo_model_controller NodesEdges

  auto_actions :all, :except => :index
  auto_actions_for :source, [:new, :create]

end
