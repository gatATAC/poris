class HoboMigration22 < ActiveRecord::Migration
  def self.up
    rename_column :nodes, :variant_description, :variation
  end

  def self.down
    rename_column :nodes, :variation, :variant_description
  end
end
