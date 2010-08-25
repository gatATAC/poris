class HoboMigration41 < ActiveRecord::Migration
  def self.up
    rename_column :node_attributes, :node, :content
  end

  def self.down
    rename_column :node_attributes, :content, :node
  end
end
