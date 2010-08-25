class HoboMigration40 < ActiveRecord::Migration
  def self.up
    drop_table :value_attributes

    create_table :node_attributes do |t|
      t.string   :name
      t.string   :node
      t.datetime :created_at
      t.datetime :updated_at
      t.integer  :node_id
    end
    add_index :node_attributes, [:node_id]
  end

  def self.down
    create_table "value_attributes", :force => true do |t|
      t.string   "name"
      t.string   "value"
      t.datetime "created_at"
      t.datetime "updated_at"
      t.integer  "value_id"
    end

    add_index "value_attributes", ["value_id"], :name => "index_value_attributes_on_value_id"

    drop_table :node_attributes
  end
end
