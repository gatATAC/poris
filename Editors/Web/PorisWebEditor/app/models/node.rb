class Node < ActiveRecord::Base

  hobo_model # Don't put anything above this

  fields do
    name         :string
    type         :integer
	  rangemin :float
	  rangemax :float
    default_float :float
    default_string :string
    date_min :timestamp
    date_max :timestamp
    default_date :timestamp

    timestamps
  end

  def self.my_mandatory_attributes
    [:name,:project_id,:node_type_id,:type]
  end
  def self.my_attributes
    ret=my_mandatory_attributes
    return ret
  end

  validates_presence_of my_mandatory_attributes

  belongs_to :default_value, :class_name => 'Value'
  belongs_to :default_mode, :class_name => 'Mode'

  belongs_to :project, :creator => true
  belongs_to :node_type

  has_many :labels, :dependent => :destroy
  has_many :node_attributes, :dependent => :destroy

  belongs_to :parent, :foreign_key => :parent_id, :class_name => 'Node'
  has_many :children, :foreign_key => :parent_id, :class_name => 'Node'
  belongs_to :root, :class_name => 'Node'

  has_many :edges_as_source, :class_name => 'NodesEdges', :foreign_key => 'source_id', :dependent => :destroy, :order => :position
  has_many :edges_as_destination, :class_name => 'NodesEdges', :foreign_key => 'destination_id'
  has_many :sources, :through => :edges_as_destination , :accessible => true
  has_many :destinations, :through => :edges_as_source ,  :order => 'nodes_edges.position',:accessible => true

  attr_accessor :style

  def self.roots
    ret = []
    self.find(:all).each { |n|
      if not n.has_parents?
        ret << n
      end
    }
    return ret
  end

  def to_s_scope_kind(sk)
    ret=self.to_s
    lb=self.labels.find_by_scope_kind_id(sk.id)
    if lb != nil
      ret=lb.name
    end
    return ret
  end

  def has_label?(sk)
    return self.labels.find_by_scope_kind_id(sk.id) != nil
  return ret

  end
  def pretree
    ret = []
    ret += sources
    sources.each { |s|
      ret += s.pretree
    }
    return ret
  end

  def subtree
    ret = []
    ret += destinations
    destinations.each { |s|
      ret += s.subtree
    }
    return ret
  end

	def has_parents?
    return sources.size > 0
  end

	def has_children?
	  return destinations.size > 0
	end

  def default_node_type
    NodeType.find_by_name(self.class.name)
  end

  def possible_node_types
    NodeType.find_all_by_totype(self.class.name)
  end

  def possible_node_type?(v)
    possible_node_types.include?(v)
  end

  # --- Permissions --- #

  def create_permitted?
    acting_user.signed_up? && project.accepts_changes_from?(acting_user)
  end

  def update_permitted?
    acting_user.signed_up? && project.accepts_changes_from?(acting_user)
  end

  def destroy_permitted?
    acting_user.signed_up? && project.accepts_changes_from?(acting_user)
  end

  def view_permitted?(attribute)
    acting_user.signed_up? #&& project.viewable_by?(acting_user)
  end

end
