class HoboMigration34 < ActiveRecord::Migration
  def self.up
    create_table :nodes_edges do |t|
      t.datetime :created_at
      t.datetime :updated_at
      t.integer  :source_id
      t.integer  :destination_id
    end
    
    add_column :nodes, :accum_count, :integer
    add_column :nodes, :rangemin, :float
    add_column :nodes, :rangemax, :float
    add_column :nodes, :default_float, :float
    add_column :nodes, :default_string, :string
    add_column :nodes, :default_value_id, :integer
    add_column :nodes, :default_mode_id, :integer
  end

  def self.down
    remove_column :nodes, :accum_count
    remove_column :nodes, :rangemin
    remove_column :nodes, :rangemax
    remove_column :nodes, :default_float
    remove_column :nodes, :default_string
    remove_column :nodes, :default_value_id
    remove_column :nodes, :default_mode_id
    
    drop_table :nodes_edges
  end
end
