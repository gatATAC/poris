class NodesController < ApplicationController

  hobo_model_controller

  auto_actions :show, :edit, :update, :destroy
  auto_actions_for :project, [:new, :create]

  autocomplete :new_member_name do
    node = find_instance
    hobo_completions :name, User.without_joined_node(node).is_not(node.owner)
  end

end
