class HoboMigration37 < ActiveRecord::Migration
  def self.up
    add_column :scope_kinds, :src_format, :string
  end

  def self.down
    remove_column :scope_kinds, :src_format
  end
end
