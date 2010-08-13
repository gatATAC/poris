class Project < ActiveRecord::Base

  hobo_model # Don't put anything above this

  fields do
    name :string, :unique
    abbrev :string, :unique
    timestamps
  end

  has_many :nodes, :dependent => :destroy
  belongs_to :owner, :class_name => "User", :creator => true
  has_many :project_memberships, :dependent => :destroy
  has_many :members, :through => :project_memberships, :source => :user
  has_many :contributor_memberships, :class_name => "ProjectMembership", :scope => :contributor
  has_many :contributors, :through => :contributor_memberships, :source => :user

  # PARCHE: Creo que estas relaciones no deberían existir
  has_many :libraries, :dependent => :destroy
  has_many :sub_systems, :dependent => :destroy


  # --- Permissions --- #

  def create_permitted?
    owner_is? acting_user
  end

  def update_permitted?
    acting_user.administrator? || (owner_is?(acting_user) && !owner_changed?)
  end

  def destroy_permitted?
    acting_user.administrator? || owner_is?(acting_user)
  end

  def view_permitted?(attribute)
    acting_user.administrator? || acting_user == owner || acting_user.in?(members)
  end

  def accepts_changes_from?(user)
    user.administrator? || user == owner || user.in?(contributors)
  end

end
