class Role < ActiveRecord::Base

  hobo_model # Don't put anything above this

  fields do
    name :string, :unique
    abbrev :string, :unique
    description :text
    timestamps
  end

  has_many :project_memberships

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
