class HoboMigration2 < ActiveRecord::Migration
  def self.up
    create_table :node_types do |t|
      t.string   :totype
      t.string   :name
      t.string   :img_link
      t.datetime :created_at
      t.datetime :updated_at
    end
  end

  def self.down
    drop_table :node_types
  end
end
