class HoboMigration52 < ActiveRecord::Migration
  def self.up
    add_column :nodes, :date_min, :timestamp
    add_column :nodes, :date_max, :timestamp
    add_column :nodes, :default_date, :timestamp
  end

  def self.down
    remove_column :nodes, :date_min
    remove_column :nodes, :date_max
    remove_column :nodes, :default_date
  end
end
