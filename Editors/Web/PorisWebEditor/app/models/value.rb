class Value < Node
  has_many :edges_as_source, :class_name => 'NodesEdges', :foreign_key => 'source_id', :dependent=>:destroy,:order => :position
  has_many :edges_as_destination, :class_name => 'NodesEdges', :foreign_key => 'destination_id'
  has_many :sources, :through => :edges_as_destination , :accessible => true
  has_many :destinations, :through => :edges_as_source ,  :order => 'nodes_edges.position', :accessible => true

	has_many :systems, :through => :edges_as_destination, :accessible => true, :source => :source, :class_name => 'SubSystem'
	has_many :modes, :through => :edges_as_destination, :accessible => true, :source => :source, :class_name => 'Mode'
  has_many :modes_as_default_value, :class_name => 'Mode', :foreign_key => 'default_value_id', :accessible => true

  validates_presence_of my_mandatory_attributes

  # PARCHE: CREO QUE NO DEBERÍA EXISTIR ESTA LÍNEA
  has_many :labels, :foreign_key => :node_id
  has_many :node_attributes, :foreign_key => :node_id

  belongs_to :value_formatter

  def self.my_attributes
    ret=super
    ret+=[:value_formatter_id]
    return ret
  end

  def self.my_mandatory_attributes
    ret=super
    #ret+=[:value_formatter_id]
    return ret
  end


  def possible_modes
    possible_modes = []
    systems.each {|s|
      s.modes.each {|p|
        if not possible_modes.include?(p)
          possible_modes << p
        end
      }
    }
    return possible_modes
  end

  def possible_modes?(v)
    possible_modes.include?(v)
  end
end
