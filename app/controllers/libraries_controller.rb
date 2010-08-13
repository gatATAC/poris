class LibrariesController < ApplicationController

  hobo_model_controller

  auto_actions :all, :except => :index
  auto_actions_for :project, [:new, :create]
  
  def new
      hobo_new do
        if (params[:super_library]) then
          suplib=Library.find(params[:super_library])
          @this.super_libs << Library.find(params[:super_library])
          @this.project=suplib.project
        else
          if (params[:project]) then
            @this.project = Project.find(params[:project])
          else
            @this.project = Project.find(:first)
          end
        end
        @this.node_type = NodeType.find_by_name("Library")
      end
    end

end
