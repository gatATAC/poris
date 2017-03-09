class HoboMigration17 < ActiveRecord::Migration
  def self.up
    remove_column :nodes, :variant_id
  end

  def self.down
    add_column :nodes, :variant_id, :integer
  end
end
