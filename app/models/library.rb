class Library < Node
  has_many :edges_as_source, :class_name => 'NodesEdge', :foreign_key => 'source_id', :dependent=>:destroy,:order => :position
  has_many :edges_as_destination, :class_name => 'NodesEdge', :foreign_key => 'destination_id'
  has_many :sources, :through => :edges_as_destination , :accessible => true
  has_many :destinations, :through => :edges_as_source , :order => 'nodes_edges.position', :accessible => true

  has_many :sub_libs, :through => :edges_as_source, :accessible => true, :source => :destination, :class_name => 'Library'
  has_many :super_libs, :through => :edges_as_destination, :accessible => true, :source => :source, :class_name => 'Library'
  has_many :systems, :through => :edges_as_source, :accessible => true, :source => :destination, :class_name => 'SubSystem'

  attr_accessible :name, :super_libs, :sub_libs

  validates_presence_of my_mandatory_attributes

  # PARCHE: CREO QUE NO DEBERÍA EXISTIR ESTA LÍNEA
  has_many :labels, :foreign_key => :node_id
  has_many :node_attributes, :foreign_key => :node_id
  #belongs_to :project, :accessible => true, :creator => true

	children :systems,:labels

  def self.my_mandatory_attributes
    ret=super
  end

  def self.my_attributes
    ret=super
    ret+=[:default_mode_id]
  end

end
