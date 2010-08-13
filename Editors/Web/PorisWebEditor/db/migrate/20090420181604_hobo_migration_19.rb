class HoboMigration19 < ActiveRecord::Migration
  def self.up
    add_column :nodes, :root_id, :integer
  end

  def self.down
    remove_column :nodes, :root_id
  end
end
