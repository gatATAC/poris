class HoboMigration27 < ActiveRecord::Migration
  def self.up
    create_table :labels do |t|
      t.string   :name
      t.datetime :created_at
      t.datetime :updated_at
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
