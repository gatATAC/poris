class ProjectsController < ApplicationController

  hobo_model_controller

  auto_actions :show, :edit, :update, :destroy
  auto_actions_for :owner, [:new, :create]

  def show
    @project = find_instance
    @nodes =
      @project.nodes
    @project_memberships =
      @project.project_memberships
    @libraries = @project.libraries
  end
end
