class HoboMigration39 < ActiveRecord::Migration
  def self.up
    create_table :value_attributes do |t|
      t.string   :name
      t.string   :value
      t.datetime :created_at
      t.datetime :updated_at
      t.integer  :value_id
    end
    add_index :value_attributes, [:value_id]
  end

  def self.down
    drop_table :value_attributes
  end
end
