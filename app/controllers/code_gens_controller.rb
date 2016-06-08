class CodeGensController < ApplicationController
  hobo_model_controller

  auto_actions :all

  def show_code
    if (params[:id])
      @code_gen=CodeGen.find(params[:id])
    else
      @code_gen = CodeGen.find(:first)
    end
    if (params[:node])
      @node = Node.find(params[:node])
    else
      @node = Node.find(:first)
    end
  end

  def show_node
    show_code
  end

  def self.get_code_gen
      code_gen = CodeGen.find(:first)
  end

end
