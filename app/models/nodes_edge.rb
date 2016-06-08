class NodesEdge < ActiveRecord::Base

  hobo_model # Don't put anything above this

  belongs_to :source, :class_name => 'Node', :creator => true
  belongs_to :destination, :class_name => 'Node'

  acts_as_list :scope => :source

  def reorder
    something to crash
  end

  fields do
    timestamps
  end

  # --- Permissions --- #

  def create_permitted?
    acting_user.signed_up?
  end

  def update_permitted?
    acting_user.signed_up?
  end

  def destroy_permitted?
    acting_user.signed_up?
  end

  def view_permitted?(field)
    true
  end

end
