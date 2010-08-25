class TreeCodeGensController < CodeGensController
  hobo_model_controller

  auto_actions :all
  
  def show_code
    if (params[:id])
      @code_gen=TreeCodeGen.find(params[:id])
    else
      @code_gen = TreeCodeGen.find(:first)
    end
    if (params[:node])
      @node = Node.find(params[:node])
    else
      @node = Node.find(:first)
    end
  end

  def self.get_code_gen
      code_gen = TreeCodeGen.find(:first)
  end
  
  def get_code_gen
      code_gen = TreeCodeGen.find(:first)
  end

end
