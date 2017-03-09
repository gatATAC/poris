class HoboMigration20 < ActiveRecord::Migration
  def self.up
    add_column :nodes, :project_id, :integer
    remove_column :nodes, :owner_id
  end

  def self.down
    remove_column :nodes, :project_id
    add_column :nodes, :owner_id, :integer
  end
end
