class HoboMigration11 < ActiveRecord::Migration
  def self.up
    add_column :tasks, :name, :string
    change_column :tasks, :description, :text, :limit => nil
  end

  def self.down
    remove_column :tasks, :name
    change_column :tasks, :description, :string
  end
end
