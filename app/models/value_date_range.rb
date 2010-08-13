class ValueDateRange < Value
  belongs_to :project, :creator => true
  
  validates_presence_of my_mandatory_attributes

  # PARCHE: CREO QUE NO DEBERÍA EXISTIR ESTA LÍNEA
  has_many :labels, :foreign_key => :node_id
  has_many :node_attributes, :foreign_key => :node_id
  #belongs_to :value_formatter
  
  def self.my_attributes
    ret=super
    ret+=[:default_date,:date_min,:date_max]
  end
  def self.my_mandatory_attributes
    ret=super
    ret+=[:default_date,:date_min,:date_max]
  end

end
