class HoboMigration36 < ActiveRecord::Migration
  def self.up
    add_column :nodes_edges, :position, :integer
  end

  def self.down
    remove_column :nodes_edges, :position
  end
end
