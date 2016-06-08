class SubSystem < Node
  has_many :edges_as_source, :class_name => 'NodesEdge', :foreign_key => 'source_id', :dependent=>:destroy,:order => :position
  has_many :edges_as_destination, :class_name => 'NodesEdge', :foreign_key => 'destination_id'
  has_many :sources, :through => :edges_as_destination , :accessible => true
  has_many :destinations, :through => :edges_as_source ,  :order => 'nodes_edges.position', :accessible => true

  has_many :values, :through => :edges_as_source, :accessible => true, :source => :destination, :class_name => 'Value'
  has_many :modes, :through => :edges_as_source, :accessible => true, :source => :destination, :class_name => 'Mode'
  has_many :sub_systems, :through => :edges_as_source, :accessible => true, :source => :destination, :class_name => 'SubSystem'
  has_many :super_systems, :through => :edges_as_destination, :accessible => true, :source => :source, :class_name => 'SubSystem'
  has_many :libraries, :through => :edges_as_destination, :accessible => true, :source => :source, :class_name => 'Library'

  belongs_to :default_mode, :class_name => 'Mode', :accessible => true

  # PARCHE: CREO QUE NO DEBERÍA EXISTIR ESTA LÍNEA
  has_many :labels, :foreign_key => :node_id
  has_many :node_attributes, :foreign_key => :node_id

	children :sub_systems, :labels

  validates_presence_of my_mandatory_attributes

  attr_accessible :name, :default_mode, :modes, :node_type, :libraries, :sub_systems, :super_systems, :values

  def self.my_mandatory_attributes
    ret=super
  end

  def self.my_attributes
    ret=super
    ret+=[:default_mode_id]
  end

  def to_complete_xml
    return self.to_xml
  end

end
