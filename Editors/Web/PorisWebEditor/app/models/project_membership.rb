class ProjectMembership < ActiveRecord::Base

  hobo_model # Don't put anything above this

  fields do
    contributor :boolean
    timestamps
  end

belongs_to :project, :accessible => true, :creator => true
belongs_to :user, :accessible => true
belongs_to :role, :accessible => true
validates_presence_of :user,:role,:project


def name
  ret = ""
  ret += user.abbrev
  ret += " as "
  ret += role.abbrev
  ret += " in "
  ret += project.abbrev
end
# --- Permissions --- #

def create_permitted?
  acting_user.administrator? || acting_user == project.owner
end

def update_permitted?
  (acting_user.administrator? || acting_user == project.owner) && !project_changed?
end

def destroy_permitted?
  acting_user.administrator? || acting_user == project.owner
end

def view_permitted?(attribute)
  project.viewable_by?(acting_user)
end

end
