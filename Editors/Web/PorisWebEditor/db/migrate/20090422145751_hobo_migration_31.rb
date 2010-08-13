class HoboMigration31 < ActiveRecord::Migration
  def self.up
    add_column :scope_kinds, :description, :text
  end

  def self.down
    remove_column :scope_kinds, :description
  end
end
