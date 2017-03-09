class HoboMigration21 < ActiveRecord::Migration
  def self.up
    add_column :nodes, :variant_description, :string
  end

  def self.down
    remove_column :nodes, :variant_description
  end
end
