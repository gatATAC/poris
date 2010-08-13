class GroupsController < ApplicationController

  hobo_model_controller

  auto_actions :all, :except => :index
  auto_actions_for :project, [:new,:create]

    show_action :detail

  def new
    hobo_new do
      if (params[:super_system]) then
        ss = SubSystem.find(params[:super_system])
        @this.super_systems << ss
        @this.project=ss.project
      end
      if (params[:library]) then
        lib=Library.find(params[:library])
        @this.libraries << lib
        @this.project=lib.project
      end
      @this.node_type = @this.default_node_type
      this.attributes = params[:mode] || {}
      hobo_ajax_response if request.xhr?
    end
  end
end
