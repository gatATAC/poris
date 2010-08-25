class HoboMigration45 < ActiveRecord::Migration
  def self.up
    add_column :node_attributes, :visibility, :boolean
  end

  def self.down
    remove_column :node_attributes, :visibility
  end
end
