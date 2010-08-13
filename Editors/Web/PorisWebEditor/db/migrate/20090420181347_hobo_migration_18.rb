class HoboMigration18 < ActiveRecord::Migration
  def self.up
    add_column :nodes, :parent_id, :integer
  end

  def self.down
    remove_column :nodes, :parent_id
  end
end
