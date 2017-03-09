class HoboMigration23 < ActiveRecord::Migration
  def self.up
    create_table :labels do |t|
      t.string   :name
      t.datetime :created_at
      t.datetime :updated_at
      t.integer  :node_id
      t.integer  :scope_kind_id
    end
    
    create_table :scope_kinds do |t|
      t.string   :name
      t.string   :type
      t.datetime :created_at
      t.datetime :updated_at
    end
  end

  def self.down
    drop_table :labels
    drop_table :scope_kinds
  end
end
