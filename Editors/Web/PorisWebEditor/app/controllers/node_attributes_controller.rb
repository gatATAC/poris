class NodeAttributesController < ApplicationController

  hobo_model_controller

  auto_actions :all, :except => :index
  auto_actions_for :value, [:new, :create]
  auto_actions_for :mode, [:new, :create]
  auto_actions_for :sub_system, [:new, :create]
  auto_actions_for :value_double_range, [:new, :create]
  auto_actions_for :value_date_range, [:new, :create]
  auto_actions_for :value_string, [:new, :create]
  auto_actions_for :value_file_path, [:new, :create]
end
