class HoboMigration24 < ActiveRecord::Migration
  def self.up
    remove_column :labels, :node_id
    
    remove_column :scope_kinds, :type
  end

  def self.down
    add_column :labels, :node_id, :integer
    
    add_column :scope_kinds, :type, :string
  end
end
