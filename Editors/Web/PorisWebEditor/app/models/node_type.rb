class NodeType < ActiveRecord::Base

  hobo_model # Don't put anything above this

  fields do
    totype   :string
    name     :string
    img_link :string
    timestamps
  end

  has_many :nodes

  validates_presence_of :totype, :name, :img_link
  
  # --- Permissions --- #

  def create_permitted?
    acting_user.administrator?
  end

  def update_permitted?
    acting_user.administrator?
  end

  def destroy_permitted?
    acting_user.administrator?
  end

  def view_permitted?(field)
    true
  end

end
