class Label < ActiveRecord::Base

  hobo_model # Don't put anything above this

  belongs_to :node, :creator => true
  # PARCHE: CREO QUE NO DEBERÍA DE REPETIR ESTAS ASOCIACIONES, QUE DEBERÍAN
  # DE HEREDARSE DE LA ASOCIACIÓN CON NODE
  belongs_to :library, :foreign_key => :node_id
  belongs_to :sub_system, :foreign_key => :node_id
  belongs_to :mode, :foreign_key => :node_id
  belongs_to :value, :foreign_key => :node_id
  belongs_to :value_string, :foreign_key => :node_id
  belongs_to :value_double_range, :foreign_key => :node_id
  belongs_to :value_date_range, :foreign_key => :node_id
  belongs_to :group, :foreign_key => :node_id
  # FIN PARCHE

  belongs_to :scope_kind

  validates_presence_of :node, :scope_kind, :name

  fields do
    name     :string
    timestamps
  end

  # --- Permissions --- #

  def create_permitted?
    acting_user.signed_up? && node.project.accepts_changes_from?(acting_user)
  end
  def update_permitted?
    acting_user.signed_up? && !node_changed? && node.project.accepts_changes_from?(acting_user)
  end

  def destroy_permitted?
    acting_user.signed_up? && node.project.accepts_changes_from?(acting_user)
  end

  def view_permitted?(attribute)
    node.viewable_by?(acting_user)
  end

end
