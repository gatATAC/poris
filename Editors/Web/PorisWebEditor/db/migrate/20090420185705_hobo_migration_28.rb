class HoboMigration28 < ActiveRecord::Migration
  def self.up
    add_column :labels, :node_id, :integer
    add_column :labels, :scope_kind_id, :integer
  end

  def self.down
    remove_column :labels, :node_id
    remove_column :labels, :scope_kind_id
  end
end
