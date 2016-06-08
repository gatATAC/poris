class FrontController < ApplicationController

  hobo_controller

  def index; end

  def summary
    if !current_user.administrator?
      redirect_to user_login_path
    end
  end

  def search
    if params[:query]
      site_search(params[:query])
    end
  end


  def tree
    @code_gen=WebTreeCodeGen.find(:first)
    if (params[:id])
      @node=Node.find(params[:id])
    else
      @node = Library.find(:first)
    end
  end

end
