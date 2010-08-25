class ScopeKind < ActiveRecord::Base

  hobo_model # Don't put anything above this

  fields do
    name :string
    description :text
    type :string
    src_format :string
    timestamps
  end

  has_many :labels, :dependent => :destroy
  
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
