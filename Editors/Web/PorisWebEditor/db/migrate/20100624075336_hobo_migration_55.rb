class HoboMigration55 < ActiveRecord::Migration
  def self.up
    add_column :nodes, :file_extension, :string
    add_column :nodes, :file_description, :string
  end

  def self.down
    remove_column :nodes, :file_extension
    remove_column :nodes, :file_description
  end
end

