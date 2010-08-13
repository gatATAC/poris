class HoboMigration35 < ActiveRecord::Migration
  def self.up
    change_column :nodes, :type, :string, :limit => 255
  end

  def self.down
    change_column :nodes, :type, :integer
  end
end
