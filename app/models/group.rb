class Group < SubSystem

  # PARCHE: CREO QUE NO DEBERÍA EXISTIR ESTA LÍNEA
  has_many :labels, :foreign_key => :node_id
  has_many :node_attributes, :foreign_key => :node_id

  def self.my_mandatory_attributes
    ret=super
  end

  def self.my_attributes
    ret=super
  end

end
