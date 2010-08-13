class HoboMigration54 < ActiveRecord::Migration
  def self.up
    remove_column :nodes, :accum_count
    remove_column :nodes, :version
    remove_column :nodes, :variation
  end

  def self.down
    add_column :nodes, :accum_count, :integer
    add_column :nodes, :version, :float
    add_column :nodes, :variation, :string
  end
end
