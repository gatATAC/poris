class ValueDoubleRange < Value
  belongs_to :project, :creator => true
  
  validates_presence_of my_mandatory_attributes

  # PARCHE: CREO QUE NO DEBERÍA EXISTIR ESTA LÍNEA
  has_many :labels, :foreign_key => :node_id
  has_many :node_attributes, :foreign_key => :node_id
  #belongs_to :value_formatter
  
  def self.my_attributes
    ret=super
    ret+=[:default_float,:rangemin,:rangemax]
  end
  def self.my_mandatory_attributes
    ret=super
    ret+=[:default_float,:rangemin,:rangemax]
  end

  children :systems

end
