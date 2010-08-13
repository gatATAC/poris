class ModesController < ApplicationController

  hobo_model_controller

  auto_actions :all

  def new
    hobo_new do
      if (params[:system]) then
        sy=SubSystem.find(params[:system])
        @this.systems << sy
        @this.project=sy.project
      end
      if (params[:super_mode]) then
        sm = Mode.find(params[:super_mode])
        @this.super_modes << sm
        @this.project=sm.project
      end
      @this.node_type = @this.default_node_type
      this.attributes = params[:mode] || {}
      hobo_ajax_response if request.xhr?
    end
  end

end
