class ValueFilePathsController < ApplicationController

  hobo_model_controller

  auto_actions :all

  def new
    hobo_new do
      if (params[:system]) then
        sys=SubSystem.find(params[:system])
        @this.systems << sys
        @this.project=sys.project
      end
      @this.node_type = NodeType.find_by_name("Value")
      #@this.value_formatter = ValueFormatter.find_by_name("none");
    end
  end

end
