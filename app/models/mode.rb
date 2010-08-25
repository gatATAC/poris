class Mode < Node

  has_many :edges_as_source, :class_name => 'NodesEdges', :foreign_key => 'source_id', :dependent => :destroy, :order => :position
  has_many :edges_as_destination, :class_name => 'NodesEdges', :foreign_key => 'destination_id'
  has_many :sources, :through => :edges_as_destination , :accessible => true
  has_many :destinations, :through => :edges_as_source , :accessible => true

  has_many :values, :through => :edges_as_source, :accessible => true, :source => :destination, :class_name => 'Value'
  has_many :systems, :through => :edges_as_destination, :accessible => true, :source => :source, :class_name => 'SubSystem'
  has_many :sub_modes, :through => :edges_as_source, :accessible => true, :source => :destination, :class_name => 'Mode'
  has_many :super_modes, :through => :edges_as_destination, :accessible => true, :source => :source, :class_name => 'Mode'

  belongs_to :default_value, :class_name => 'Value',:accessible => true
  belongs_to :default_mode, :class_name => 'Mode', :accessible => true
  has_many :sub_systems_as_default_mode, :class_name => 'SubSystem', :foreign_key => 'default_mode_id', :accessible => true
  has_many :super_modes_as_default_mode, :class_name => 'Mode', :foreign_key => 'default_mode_id', :accessible => true

  # PARCHE: CREO QUE NO DEBERÍA EXISTIR ESTA LÍNEA
  has_many :labels, :foreign_key => :node_id
  has_many :node_attributes, :foreign_key => :node_id

  def self.my_mandatory_attributes
    ret=super
  end

  def self.my_attributes
    ret=super
    ret+=[:default_value_id]
    ret+=[:default_mode_id]
  end

  validates_presence_of my_mandatory_attributes

  def possible_values
    possible_values = []
    systems.each {|s|
      s.values.each {|p|
        if not possible_values.include?(p)
          possible_values << p
        end
      }
    }
    return possible_values
  end

  def possible_super_modes(systs)
    if systs.length > 0 then
      possible_modes = []
      systs.each {|s|
      print "\n"+ s.name + ": "
      s.super_systems.each {|ss|
        ss.modes.each {|p|
          print p.name + " "
          if not possible_modes.include?(p)
            possible_modes << p
          end
        }
      }
    }
    else
      possible_modes = Mode.find(:all)
    end
    return possible_modes
  end

  def possible_sub_modes(systs)
    if systs.length > 0 then
      possible_modes = []
      systs.each {|s|
      print "\n"+ s.name + ": "
      s.sub_systems.each {|ss|
        ss.modes.each {|p|
          print p.name + " "
          if not possible_modes.include?(p)
            possible_modes << p
          end
        }
      }
    }
    else
      possible_modes = Mode.find(:all)
    end
    return possible_modes
  end

  def possible_systems(supermodes)
    if supermodes.length > 0 then
    possible_systems = []
    supermodes.each {|sm|
      sm.systems.each {|ss|
        ss.sub_systems.each {|p|
          if not possible_systems.include?(p)
            possible_systems << p
          end
        }
      }
    }
    else
      possible_systems = SubSystem.find(:all)
    end
    return possible_systems
  end

end
