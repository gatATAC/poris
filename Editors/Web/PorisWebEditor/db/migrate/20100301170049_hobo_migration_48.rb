class HoboMigration48 < ActiveRecord::Migration
  def self.up
    change_column :node_attributes, :visibility, :boolean, :default => true
  end

  def self.down
    change_column :node_attributes, :visibility, :boolean, :default => false
  end
end
